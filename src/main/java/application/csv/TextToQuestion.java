package application.csv;

import application.models.Question;
import com.opencsv.bean.AbstractCsvConverter;

/**
 * Adapter class for converting the Question Bean from/to CSV
 */
public class TextToQuestion extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) {
        //TODO: to be implemented
        return null;
    }

    @Override
    public String convertToWrite(Object value) {
        Question  q = (Question) value;
        return String.format("%s|%s|%s|%s", q.getQuestion(), q.getType(), q.optionToDSV(), q.answersToDSV());
    }
}
