package com.cancogang69.todo.enums;

public enum TaskStatus {
  INITIAL,
  ONGOING,
  COMPLETED;

  public static TaskStatus fromJson(String jsonStr) {
    String regex = "[{}:\"]";
    String[] fields = jsonStr.split(regex);
    int statusValueIdx = 5;
    return TaskStatus.valueOf(fields[statusValueIdx]);
  }
}
