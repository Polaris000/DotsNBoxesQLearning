package engine;
import java.util.Set;
import java.util.HashSet;

public class Grid
{
    
    public static final int default_size = 4;

    private int size;

    private boolean[] line_ids;

    private Set<Integer> available;


    public Grid()
    {
        this(default_size);
    }

    public Grid(int size)
    {
        this.size = size;

        int numLines = 2 * size * (size - 1);
        line_ids = new boolean[numLines];
        available = new HashSet<Integer>();
    }


    public long get_id()
    {
        int sum = 0;

        for (int i = 0; i < line_ids.length; ++i)
            if (line_ids[i])
                sum += 1 << i;

        return sum;
    }


    public boolean is_finished()
    {
        for (int i = 0; i < line_ids.length; ++i)
            if (!line_ids[i])
                return false;
        return true;
    }

    public Set<Integer> get_available()
    {
        available.clear();

        for (int i = 0; i < line_ids.length; ++i)
            if (!line_ids[i])
                available.add(i);

        return available;
    }


    public int play_line(int line)
    {
        if (line < 0 || line >= line_ids.length) {
            System.out.println(String.format("Invalid line number %d supplied. " +
                        "Valid interval is [%d, %d].", line, 0,
                        line_ids.length - 1));
            return -1;
        }

        line_ids[line] = true;
        int sum = 0;

        if (is_horiz(line))
        {
            if (line >= size
                    && line_ids[line - (2 * size - 1)]
                    && line_ids[line - size]
                    && line_ids[line - size + 1])
                ++ sum;

            if (line < line_ids.length - size
                    && line_ids[line + size - 1]
                    && line_ids[line + size]
                    && line_ids[line + (2 * size - 1)])
                ++ sum;
        }
        
        else
        {
            if ((line % (2 * size - 1)) - (size - 1) != (size - 1)
                    && line_ids[line + 1]
                    && line_ids[line - size + 1]
                    && line_ids[line + size])
                ++ sum;

            if ((line % (2 * size - 1)) - (size - 1) != 0
                    && line_ids[line - 1]
                    && line_ids[line - size]
                    && line_ids[line + size - 1])
                ++ sum;
        }

        return sum;
    }


    private boolean is_horiz(int n)
    {
        return n % (2 * size - 1) < size - 1;
    }


    public void reset()
    {
        for (int i = 0; i < line_ids.length; ++i)
            line_ids[i] = false;
    }


    public int get_size()
    {
        return line_ids.length;
    }

    public long get_max_id()
    {
        int sum = 0;
        for (int i = 0; i < line_ids.length; ++i)
            sum += 1 << i;

        return sum;
    }
}
