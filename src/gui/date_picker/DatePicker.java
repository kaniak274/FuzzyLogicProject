package gui.date_picker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class DatePicker {
    public static JDatePickerImpl createDatePicker() {

        UtilDateModel model = getModel();

        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        return new JDatePickerImpl(datePanel);
    }
    
    public static UtilDateModel getModel() {
        UtilDateModel model = new UtilDateModel();
        model.setDate(1992, 0, 1);
        model.setSelected(true);
        
        return model;
    }
    
    public static Date getDate(JDatePickerImpl dp) {
        return (Date) dp.getModel().getValue();
    }
    
    public static Date getDate(String dp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            return formatter.parse(dp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
