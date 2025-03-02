package edu.berkeley.cs.jqf.examples;

public class IsThisSubtaskShouldSubscribeTo {
public static boolean isThisSubtaskShouldSubscribeTo(int shardHash,
														int totalNumberOfConsumerSubtasks,
														int indexOfThisConsumerSubtask) {
		return (Math.abs(shardHash % totalNumberOfConsumerSubtasks)) == indexOfThisConsumerSubtask;
	}
public static void main(String[] args) {


isThisSubtaskShouldSubscribeTo(97, 36, 24);
}
}