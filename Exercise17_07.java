import java.io.*;

public class Exercise17_07 { 
    public static void main(String[] args) throws FileNotFoundException { 
        Loan loan1 = new Loan(); 
        Loan loan2 = new Loan(1.8, 10, 10000); 

        try ( 
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Exercise17_07.dat")); 
        ) { 
            output.writeObject(loan1); 
            output.writeObject(loan2); 
        } catch (IOException ex) { 
            System.out.println("File could not be opened"); 
        } 

        // Read and process the serialized objects
        outputData();
    } 

    /** Reads Loan objects from the file and displays the total loan amount */
    public static void outputData() {
        double totalLoanAmount = 0.0;

        try (
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("Exercise17_07.dat"));
        ) {
            // Read until EOFException breaks the loop
            while (true) {
                Loan loan = (Loan) input.readObject();
                totalLoanAmount += loan.getLoanAmount();
            }
        } catch (EOFException ex) {
            // Expected catch block when file ends
            System.out.printf("Total loan amount: $%.2f\n", totalLoanAmount);
        } catch (ClassNotFoundException ex) {
            System.out.println("Loan class could not be found.");
        } catch (IOException ex) {
            System.out.println("An I/O error occurred while reading the file.");
        }
    }
}

/** Rewritten Loan class implementing Serializable */
class Loan implements Serializable { 
    private static final long serialVersionUID = 1L;
    
    private double annualInterestRate; 
    private int numberOfYears; 
    private double loanAmount; 
    private java.util.Date loanDate; 

    /** Default constructor */ 
    public Loan() { 
        this(2.5, 1, 1000); 
    } 

    /** Construct a loan with specified properties */ 
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) { 
        this.annualInterestRate = annualInterestRate; 
        this.numberOfYears = numberOfYears; 
        this.loanAmount = loanAmount; 
        loanDate = new java.util.Date(); 
    } 

    /** Return annualInterestRate */ 
    public double getAnnualInterestRate() { 
        return annualInterestRate; 
    } 

    /** Set a new annualInterestRate */ 
    public void setAnnualInterestRate(double annualInterestRate) { 
        this.annualInterestRate = annualInterestRate; 
    } 

    /** Return numberOfYears */ 
    public int getNumberOfYears() { 
        return numberOfYears; 
    } 

    /** Set a new numberOfYears */ 
    public void setNumberOfYears(int numberOfYears) { 
        this.numberOfYears = numberOfYears; 
    } 

    /** Return loanAmount */ 
    public double getLoanAmount() { 
        return loanAmount; 
    } 

    /** Set a new loanAmount */ 
    public void setLoanAmount(double loanAmount) { 
        this.loanAmount = loanAmount; 
    } 

    /** Find monthly payment */ 
    public double getMonthlyPayment() { 
        double monthlyInterestRate = annualInterestRate / 1200; 
        double monthlyPayment = loanAmount * monthlyInterestRate / (1 - (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12))); 
        return monthlyPayment; 
    } 

    /** Find total payment */ 
    public double getTotalPayment() { 
        double totalPayment = getMonthlyPayment() * numberOfYears * 12; 
        return totalPayment; 
    } 


    public java.util.Date getLoanDate() { 
        return loanDate; 
    } 
}
