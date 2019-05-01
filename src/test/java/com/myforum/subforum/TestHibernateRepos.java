/*package com.myforum.subforum;

// imports
import org.junit.Test;
import org.junit.BeforeClass;
import com.myforum.subforum.repositories.*;
import com.myforum.subforum.entities.*;

import java.time.Instant;

public class TestHibernateRepos {

  // what tests should I have for the repos to know if they work?
  // lets start with adds and just see if I can add some stuff to each one and then get it back

  static ModListEntity mod;
  static SubforumEntity subforum;
  static SubforumSettingsEntity subforumSettings;
  static SubscriptionEntity subscription;

  static ModListRepo modRepo;
  static SubforumRepo subforumRepo;
  static SubforumSettingsRepo subforumSettingsRepo;
  static SubscriptionRepo subscriptionRepo;

  @BeforeClass
  public static void setup() {
    modRepo = new ModListRepo();
    subforumRepo = new SubforumRepo();
    subforumSettingsRepo = new SubforumSettingsRepo();
    subscriptionRepo = new SubscriptionRepo();

    subforum = new SubforumEntity();
    subforum.setName("asdf");
    subforum.setWebAddress("/asdf");
    subforum.setDescription("testing description");
    subforum.setFlair("test flair");
    subforum.setSubscriberCount(0);
    subforum.setCreatedDate(Instant.now());
    subforum.setCreatedBy(8);

    subforum = subforumRepo.add(subforum);

    mod = new ModListEntity();
    mod.setUserId(8);
    mod.setSubforumId(subforum.getId());
    mod.setRank(1);

    mod = modRepo.add(mod);

    subforumSettings = new SubforumSettingsEntity();
    subforumSettings.setSubforumId(subforum.getId());
    subforumSettings.setRules("test rules");

    subforumSettings = subforumSettingsRepo.add(subforumSettings);

    subscription = new SubscriptionEntity();
    subscription.setSubforumId(subforum.getId());
    subscription.setUserId(8);

    subscription = subscriptionRepo.add(subscription);

    System.out.println(subforum.getId() + " : " + mod.getId() + " : " + subforumSettings.getId() + " : " + subscription.getId());

  }

  @Test
  public void testTest() {

  }

} */
