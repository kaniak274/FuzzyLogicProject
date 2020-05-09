package gui;

import java.util.Date;

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
}
