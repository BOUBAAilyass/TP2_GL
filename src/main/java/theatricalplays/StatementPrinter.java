package theatricalplays;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {
    public static final String TRAGEDY= "tragedy";
    public static final String COMEDY = "comedy";

  public String print(Invoice invoice, Map<String, Play> plays) {
    


    int totalAmount = 0;
    int volumeCredits = 0;
    
    StringWriter writer = new StringWriter();
    PrintWriter printer = new PrintWriter(writer);
    printer.println(String.format("Statement for %s", invoice.customer));
    
    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
    
    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      int thisAmount = 0;
    // calculate amount 
      switch (play.type) {
        // calculate amount for a tragedy
        case TRAGEDY:
          thisAmount = 40000;
          if (perf.audience > 30) {
            thisAmount += 1000 * (perf.audience - 30);
          }
          break;
        // calculate amount for a comedy
        case COMEDY:
          thisAmount = 30000;
          if (perf.audience > 20) {
            thisAmount += 10000 + 500 * (perf.audience - 20);
          }
          thisAmount += 300 * perf.audience;
          break;
        // throw error for unknown type
        default:
          throw new Error("unknown type: ${play.type}");
      }

      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if ("comedy".equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // print line for this order
      printer.println(String.format("  %s: %s (%s seats)", play.name, frmt.format(thisAmount / 100), perf.audience));
      totalAmount += thisAmount;
    }
    printer.println(String.format("Amount owed is %s", frmt.format(totalAmount / 100)));
    printer.println(String.format("You earned %s credits", volumeCredits));
    String result = writer.toString();
    return result;
  }

}
