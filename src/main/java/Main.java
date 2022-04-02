import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static boolean[][][] combination_table;
    static boolean possible(int index, int curr_sum, int curr_size, int[] Vec, int size) { //functia care ne spune daca putem sa folosim elementul curent ca sa construim un subset
                                                                                          //de size curr_size si suma curr_suma
        if (curr_size == 0)
            return (curr_sum == 0);
        if (index >= size)
            return false;
        if (!combination_table[index][curr_sum][curr_size])//nu se poate face sum pentru size current
            return false;

        if (curr_sum >= Vec[index]) {//daca suma dorita este mai mare vom mari indexul si vom cauta in partea mai mare din matrice
            if (possible(index + 1, curr_sum - Vec[index], curr_size - 1, Vec, size))//continuam sa cautam in partea din dreapta unde avem elementele mai mari
                return true;
        }

        if (possible(index + 1, curr_sum, curr_size, Vec, size))//cazul de oprire cu succes cand elementul curent este ultimul necesar din suma
            return true;

        // daca am ajus nu se poate gasii solutie
        combination_table[index][curr_sum][curr_size] = false;
        return false;
    }
    static boolean split_check(int[] Vec) {

        Arrays.sort(Vec);
        int total_sum = 0;
        int size = Vec.length;

        for (int value : Vec) total_sum += value;

        combination_table = new boolean[size][total_sum + 1][size];//vom folosi un tabel care memoreaza posibilele combinatii de numere si sumele lor

        for (int i = 0; i < size; i++)
            for (int j = 0; j < total_sum + 1; j++)
                for (int k = 0; k < size; k++)
                    combination_table[i][j][k] = true;

        for (int i = 1; i < size; i++) {//parcurgem tot vectorul si verificam cu ce elemente putem crea multimi
            if ((total_sum * i) % size != 0)//pentru ca daca am folosi mediile, ar rezulta numere float care nu se reprezinta exact in memorie vom folosii inmultirea si nu impartirea
                continue;//daca suma nu este divizibila cu size, putem continua, iar la urmatoarea iteratie testam elementele
            int Sum_of_Set1 = (total_sum * i) / size;

            if (possible(0, Sum_of_Set1, i, Vec, size)) //testam daca putem creea 2 seturi cu suma egala de size s
                return true;
        }
        // daca nu am gasit nimic pana aici atunci nu este posibil sa divizam vectorul
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input_scanner = new Scanner(new File("input.in"));//input este dat la fel ca si in exemplu
        int[] input = new int[50];

        int j = 0;
        String in = input_scanner.next();
        String[] arrOfStr = in.split(",");
        int size = arrOfStr.length;
        arrOfStr[0] = arrOfStr[0].substring(1);
        arrOfStr[size - 1] = arrOfStr[size - 1].replace("]", "");

        for (String a : arrOfStr) {
            input[j] = Integer.parseInt(a);
            j++;
        }
        int[] Vec = new int[size];
        System.arraycopy(input, 0, Vec, 0, size);
        System.out.println(split_check(Vec));
    }
}
