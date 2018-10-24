package handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import until.Sight;

public class HandlerTimeSights implements Comparator<Sight> {

	@Override
	public int compare(Sight o1, Sight o2) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date o1PublishDate = null;
		Date o2PublishDate = null;
		try {
            o1PublishDate = formatter.parse(o1.getTime());
            o2PublishDate = formatter.parse(o2.getTime());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return o1PublishDate.compareTo(o2PublishDate);

    }
}
