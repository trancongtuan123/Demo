package handler;

import java.util.Comparator;

import until.Sight;

public class HandlerNameSight implements Comparator<Sight> {

	@Override
	public int compare(Sight o1, Sight o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
