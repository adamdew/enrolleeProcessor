package enrolleeProcessor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultiColumnCsvSort
{
    private static final String COLUMN_SEPARATOR = ",";

    public MultiColumnCsvSort(String fileName, Boolean deDupeOnUserID, int... indices) throws Exception
    {
        InputStream inputStream = new FileInputStream(fileName);
        List<List<String>> lines = readCsv(inputStream);

        Comparator<List<String>> comparator = createComparator(indices);
        Collections.sort(lines, comparator);

        OutputStream outputStream = new FileOutputStream(fileName);

        if(deDupeOnUserID == true){
            writeDeDupedCsv(lines, outputStream);     
        } else {
            writeCsv(lines, outputStream);
        }
           
    }

    private static List<List<String>> readCsv(
        InputStream inputStream) throws IOException
    {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream));
        List<List<String>> lines = new ArrayList<List<String>>();

        // Skip header
        String line;

        while (true)
        {
            line = reader.readLine();
            if (line == null)
            {
                break;
            }
            List<String> list = Arrays.asList(line.split(COLUMN_SEPARATOR));
            lines.add(list);
        }
        return lines;
    }

    private static void writeCsv(
        List<List<String>> lines, OutputStream outputStream) 
        throws IOException
    {
        Writer writer = new OutputStreamWriter(outputStream);
        for (List<String> list : lines)
        {
            for (int i = 0; i < list.size(); i++)
            {
                writer.write(list.get(i));
                if (i < list.size() - 1)
                {
                    writer.write(COLUMN_SEPARATOR);
                }
            }
            writer.write("\n");
        }
        writer.close();

    }

    private static void writeDeDupedCsv(
        List<List<String>> lines, OutputStream outputStream) 
        throws IOException
    {
        Writer writer = new OutputStreamWriter(outputStream);

        for(int x = 1; x < lines.size(); x++){
            
            List<String> currentLine = lines.get(x);
            List<String> previousLine = lines.get(x - 1);

            if(currentLine.get(0).equals(previousLine.get(0))){
                lines.remove(x - 1);
                x = x - 1;
            }

        }

        for (List<String> list : lines){


            for (int i = 0; i < list.size(); i++)
            {
                writer.write(list.get(i));
                if (i < list.size() - 1)
                {
                    writer.write(COLUMN_SEPARATOR);
                }
            }
            writer.write("\n");
        }
        writer.close();

    }

    private static <T extends Comparable<? super T>> Comparator<List<T>> 
        createComparator(int... indices)
    {
        return createComparator(MultiColumnCsvSort.<T>naturalOrder(), indices);
    }

    private static <T extends Comparable<? super T>> Comparator<T>
        naturalOrder()
    {
        return new Comparator<T>()
        {
            @Override
            public int compare(T t0, T t1)
            {
                return t0.compareTo(t1);
            }
        };
    }

    private static <T> Comparator<List<T>> createComparator(
        final Comparator<? super T> delegate, final int... indices)
    {
        return new Comparator<List<T>>()
        {
            @Override
            public int compare(List<T> list0, List<T> list1)
            {
                for (int i = 0; i < indices.length; i++)
                {
                    T element0 = list0.get(indices[i]);
                    T element1 = list1.get(indices[i]);
                    int n = delegate.compare(element0, element1);
                    if (n != 0)
                    {
                        return n;
                    }
                }
                return 0;
            }
        };
    }
}