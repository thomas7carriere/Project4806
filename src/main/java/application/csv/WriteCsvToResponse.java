package application.csv;

import application.models.Question;
import application.models.Survey;
import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Collection;

//@Slf4j lombok!
public class WriteCsvToResponse {

    private static final Logger log = LoggerFactory.getLogger(WriteCsvToResponse.class);

    /**
     * Print a summary for all answers to this survey.
     *
     * For OpenEndedQuestion and RangeQuestion, all answers are listed
     * For MultipleChoiceQuestion a summary us printer
     *
     * @param writer PrintWriter to be used for printing the summary
     * @param survey targeted survey
     */
    public static void writeSurveySummary(PrintWriter writer, Survey survey) {
            // need a better way to get question number...
            int counter = 1;
            for (Question q: survey.getQuestions()) {
                writer.printf("Q%d. %s", counter++, q.getQuestion());
                writer.println();
                q.getAnswerSummaryForExport().forEach(a -> writer.println(a));
                // just like survey monkey, add 3 new lines
                writer.println();
                writer.println();
                writer.println();
            }
    }

    /**
     * Export a Collection of surveys into a CSV file
     *
     * @param writer PrintWriter to be used for printing the csv
     * @param surveys Collection of surveys to be converted
     */
    public static void writeSurveys(PrintWriter writer, Collection<Survey> surveys) {
        try {
            ColumnPositionMappingStrategy<Survey> mapStrategy
                    = new ColumnPositionMappingStrategy<>();
            mapStrategy.setType(Survey.class);
            StatefulBeanToCsv<Survey> btcsv = new StatefulBeanToCsvBuilder<Survey>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();

            btcsv.write(Lists.newArrayList(surveys));
        } catch (CsvException ex) {
            log.error("Error mapping Bean to CSV", ex);
        }
    }
}
