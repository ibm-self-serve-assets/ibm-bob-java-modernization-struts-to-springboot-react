#!/usr/bin/env python3
"""
transform_passwords.py
Reads HSQLDB-exported CUSTOMER CSV, hashes PASSWD column with BCrypt,
and writes a PostgreSQL-ready CSV.

Usage: python3 transform_passwords.py --input customers.csv --output customers_migrated.csv
"""
import argparse
import csv
import sys

try:
    import bcrypt
except ImportError:
    print("Install bcrypt: pip3 install bcrypt")
    sys.exit(1)


def transform(input_file: str, output_file: str):
    with open(input_file, 'r', newline='', encoding='utf-8') as infile:
        reader = csv.DictReader(infile)
        rows = list(reader)

    if not rows:
        print("Input file is empty.")
        return

    print(f"Processing {len(rows)} customer records...")

    out_fieldnames = [
        'ssn', 'password_hash', 'first_name', 'last_name', 'gender',
        'date_of_birth', 'mobile_no', 'email', 'city', 'blood_group',
        'driving_licence', 'enabled'
    ]

    # Detect HSQLDB column names (case-insensitive lookup)
    def get_col(row, *names):
        for name in names:
            for k in row.keys():
                if k.upper() == name.upper():
                    return row[k]
        return ''

    with open(output_file, 'w', newline='', encoding='utf-8') as outfile:
        writer = csv.DictWriter(outfile, fieldnames=out_fieldnames)
        writer.writeheader()

        for i, row in enumerate(rows):
            passwd = get_col(row, 'PASSWD', 'passwd', 'password')
            if passwd:
                hashed = bcrypt.hashpw(passwd.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
            else:
                # Default password for empty entries
                hashed = bcrypt.hashpw(b'changeme!', bcrypt.gensalt()).decode('utf-8')

            enabled_raw = get_col(row, 'enabled', 'ENABLED')
            enabled = 'true' if str(enabled_raw) in ('1', 'true', 'TRUE', '') else 'false'

            writer.writerow({
                'ssn':            get_col(row, 'SSN'),
                'password_hash':  hashed,
                'first_name':     get_col(row, 'FNAME', 'firstname', 'first_name'),
                'last_name':      get_col(row, 'LNAME', 'lastname', 'last_name'),
                'gender':         get_col(row, 'GENDER'),
                'date_of_birth':  get_col(row, 'DOB', 'dateofbirth', 'date_of_birth'),
                'mobile_no':      get_col(row, 'mobileno', 'MOBILENO', 'mobile_no'),
                'email':          get_col(row, 'EMAIL'),
                'city':           get_col(row, 'CITY'),
                'blood_group':    get_col(row, 'BLOODGROUP', 'blood_group'),
                'driving_licence':get_col(row, 'DL', 'driving_licence'),
                'enabled':        enabled,
            })

            if (i + 1) % 100 == 0:
                print(f"  Processed {i + 1}/{len(rows)} records...")

    print(f"Done. Output written to: {output_file}")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Transform HSQLDB CUSTOMER passwords to BCrypt hashes')
    parser.add_argument('--input', required=True, help='Input CSV from HSQLDB export')
    parser.add_argument('--output', required=True, help='Output CSV for PostgreSQL import')
    args = parser.parse_args()
    transform(args.input, args.output)
