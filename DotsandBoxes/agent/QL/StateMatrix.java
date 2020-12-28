package agent.QL;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StateMatrix implements Serializable
{
    
    String filename;
    float matrix[][];
    static final float DEFAULT_Q_VALUE = -1.0f;

    private StateMatrix(String filename, float matrix[][])
    {
        this.filename = filename;
        this.matrix = matrix;
    }

    public float get_q_value(long state, int action)
    {
        return matrix[(int)state][action];
    }

    public void print(float [][]matrix)
    {
        for(int i=0; i<  matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j] + "    ");
            System.out.println();
        }
    }

    public void set_q_value(long state, int action, float value)
    {
        matrix[(int)state][action] = value;
        // print(matrix);
    }

    public float get_max_q(long state)
    {
        float max = Float.MIN_VALUE;

        for (int i = 0; i < matrix[(int)state].length; ++i)
            if (matrix[(int)state][i] > max)
                max = matrix[(int)state][i];

        return max;
    }

    public static StateMatrix create_table(String filename, long numStates,
            int numActions) throws OutOfMemoryError
    {
        float matrix[][] = new float[(int)numStates][numActions];

        for (int i = 0; i < numStates; ++i)
        {
            for (int j = 0; j < numActions; ++j)
            {
                matrix[i][j] = DEFAULT_Q_VALUE;
            }
        }

        return new StateMatrix(filename, matrix);
    }


    public static StateMatrix load_table(String filename) throws IOException, OutOfMemoryError
    {
        StateMatrix stateMatrix;
        ObjectInputStream objStream = new ObjectInputStream(
                new FileInputStream(filename));

        try
        {
            stateMatrix = (StateMatrix) objStream.readObject();
        }
        
        catch (ClassNotFoundException e)
        {
            throw new IOException(e.getMessage() + "dkfjkdjf");
        }
        
        finally
        {
            objStream.close();
        }

        return stateMatrix;
    } 

    public static void save_table(String filename, StateMatrix obj)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        }
      
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}