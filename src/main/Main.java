package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import db.Repository;
import gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                MainWindow window = new MainWindow(repo);
                window.createWindow();
            }
        });

        // List<Entry<Date, Double>> data = createSet(repo.getAllObjects(), "getTemperature");
        // Temperature tempFuzzy = new Temperature(data);
        // Entry<Term, FuzzySet> hotTerm = tempFuzzy.hotSetWithTerm();
        
        // double membership = hotTerm.getValue().getFuzzySet().get(140).getMembership();
        
        // System.out.println(getSubject(tempFuzzy.wasHot(membership)) + hotTerm.getKey().getLabel());
        
        /* List<Entry<Date, Double>> data2 = createSet(repo.getDaysBetweenDates(repo.formatDate("1992-01-01"), repo.formatDate("1992-01-31")), "getTemperature");
        System.out.println(data2);

        Temperature tempFuzzy2 = new Temperature(data2);
        Entry<Term, FuzzySet> hotTerm2 = tempFuzzy2.hotSetWithTerm();
        
        double membership2 = hotTerm2.getValue().getFuzzySet().get(0).getMembership();
                
        String count = AbsoluteQ.exactMatching(hotTerm2.getValue(), new Matcher() {
            @Override
            public boolean matcher(double membership) {
                return tempFuzzy2.wasHot(membership);
            }
        });
        
        System.out.println(count + getPluralSubject(true) + hotTerm2.getKey().getPluralLabe());
        
        Pressure presFuzzy = new Pressure(data2);
        Entry<Term, FuzzySet> pressureTerm = presFuzzy.highSetWithTerm();
        
        String relative = RelativeQ.quantifyOr(hotTerm2.getValue(), pressureTerm.getValue(), new Matcher() {
        	@Override
        	public boolean matcher(double membership) {
        		return membership >= 0.5;
        	}
        });
        
        System.out.println(relative + getPluralSubject(true) + hotTerm2.getKey().getPluralLabe() + orLabel() + pressureTerm.getKey().getPluralLabe()); */
    }
}
