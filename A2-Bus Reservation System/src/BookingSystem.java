/**
 * This class represents the main entry point for the booking system.
 */
public class BookingSystem {
    /**
     * The main method of the booking system. It reads the input file, processes the commands,
     * and writes the output to the output file.
     *
     * @param args The command line arguments. The first argument is the path to the input file,
     *             and the second argument is the path to the output file.
     */
    public static void main(String[] args) {
        //Note that args[0] is the first command-line-arguments as opposed to Python's one-indexed (argv[1]) approach.
        if(args.length!=2){
            System.out.print("ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            return;
        }
        if(FileInput.readFile(args[0], true, false)==null){
            System.out.print("ERROR: This program cannot read from the \""+args[0]+"\", either this program does not have read permission to read that file or file does not exist. Program is going to terminate!");
            return;
        }
        String[] reservationInput = FileInput.readFile(args[0], true, false); // Reads the file as it is without discarding or trimming anything and stores it in string array namely content.
        FileOutput.writeToFile(args[1], "", false, false);
        for(int i=0;i<reservationInput.length-1;i++){
            FileOutput.writeToFile(args[1], "COMMAND: " + reservationInput[i].trim()+"\n", true, false);
            BookingManager.commandControl(args,reservationInput[i].trim().split("\t")[0],reservationInput[i].trim());
        }
        FileOutput.writeToFile(args[1], "COMMAND: " + reservationInput[reservationInput.length-1].trim()+"\n", true, false);
        if (!(reservationInput[reservationInput.length-1].trim().equals("Z_REPORT"))){
            BookingManager.commandControl(args,reservationInput[reservationInput.length-1].trim().split("\t")[0],reservationInput[reservationInput.length-1].trim());
            BookingManager.printLastZReport(args);
        }
        else{
            BookingManager.printLastZReport(args);
        }
    }
}