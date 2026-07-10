package com.pks.insurance.service;

import java.util.Map;

public interface PremiumManager {

	Map<String, Object> getQuote(String ssn);

}
