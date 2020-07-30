package de.devfriday;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TrainTest {

  @Test
  public void whenTrainIsInterSomething_thenIsFernverkehrIsTrue() {
    Train t1 = new Train(0L, "ICE");
    Train t2 = new Train(1L, "ICX");
  }

  @Test
  public void whenTrainIsRegionalSomething_thenIsFernverkehrIsFalse() {
    Train t1 = new Train(0L, "RE");
    Train t2 = new Train(1L, "RB");
  }

}