package com.streamApp.toolkit.testutils;

import com.navercorp.fixturemonkey.FixtureMonkey;

public class TestFixtures {
  public static final FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.builder()
      .defaultNotNull(true)
      .build();
}