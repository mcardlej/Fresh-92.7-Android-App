package com.fresh927nowplaying;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Song {
	public List<String> artists = new ArrayList<String>();
	public String title;
	public String when;

	public Song(String title, String when) {
		this.title = title;
		this.when = when;
	}

	public void addArtist(String artist) {
		this.artists.add(artist);
	}

	public int getInterval() {
		Calendar calendar = new GregorianCalendar();

		Date now = new Date();

		String[] parts = this.when.split(":");

		Integer hour = new Integer(parts[0]);

		if (hour > calendar.get(Calendar.HOUR_OF_DAY)) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, new Integer(parts[1]));
		calendar.set(Calendar.SECOND, new Integer(parts[2]));

		Date songDate = calendar.getTime();

		double diffMs = now.getTime() - songDate.getTime();

		return (int) (diffMs / 1000);
	}

	public String getFormattedDuration() {
		int interval = this.getInterval();

		int h = interval / 3600;
		int m = (interval / 60) % 60;
		int s = interval % 60;

		if (h > 0) {
			return String.format("%dh ago", h);
		} else if (m > 0) {
			return String.format("%dm ago", m);
		} else {
			return String.format("%ds ago", s);
		}
	}

	@Override
	public String toString() {
		if (this.artists.size() == 0) {
			return String.format("title=%s, when=%s interval=%s",
					this.title, this.when,
					this.getFormattedDuration());
		} else {
			return String.format("artist=%s, title=%s, when=%s interval=%s",
					this.artists.get(0), this.title, this.when,
					this.getFormattedDuration());
		}
	}
}
