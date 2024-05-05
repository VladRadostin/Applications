// Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
// Фамилия Имя Отчество датарождения номертелефона пол
// Форматы данных:
// фамилия, имя, отчество - строки
// дата_рождения - строка формата dd.mm.yyyy
// номер_телефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.
// Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
// Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. 
// Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
// <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
// Не забудьте закрыть соединение с файлом.
// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {

    private static final int Fields_numbers = 6;
    public static String[] Fields = new String[6];


    private static void WelcomeMessage() {  
        
        String[] Message = new String[] {"Enter the data", 
        "Last name, first name, patronymic", "Date of birth", 
        "phone number", "gender - Latin character f or m."};

        System.out.println("");

        for (int i = 0; i < Message.length; i++){
            System.out.println(Message[i]);
        }

    }

    private static void input(){

        System.out.println("");

        Scanner scanner = new Scanner((System.in));
        String input = scanner.nextLine();
        scanner.close();
        
        Fields = input.split(" ");
        
    }

    private static void PrintMessage(String Message) {

        System.out.println("");
        System.out.println(Message);
    }


    public static void main(String[] args) throws Exception {

        WelcomeMessage();
        input();

        if(Fields.length == Fields_numbers) {
            
            String LastName = Fields[0];
            String FirstName = Fields[1];
            String patronymic = Fields[2];
            String gender = Fields[5];

            LocalDate DateOfBirth;
            long PhoneNumber;

            String filename = LastName + ".txt";

            if ((!"m".equals(gender)) && (!"f".equals(gender))) {
                PrintMessage("Invalid gender format, enter f or m");
                return;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                DateOfBirth = LocalDate.parse(Fields[3], formatter);
            } 
            catch (DateTimeParseException e)  {
                PrintMessage("Invalid date format");
                return;
            }

            try {
                PhoneNumber = Long.parseLong(Fields[4]);
            } 
            catch (NumberFormatException e)  {
                PrintMessage("Incorrect phone format");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.write(LastName + " " + FirstName + " " + patronymic + " " + DateOfBirth + " " + PhoneNumber + " " + gender);
                writer.newLine();
            } 
            catch (IOException e)  {
                PrintMessage("Recording error");
                return;
            }
                
            

        } else {
            PrintMessage("The number of fields you entered is incorrect " + Fields.length + " enter 6 fields");
            return;
        }
        

    }


    

}
