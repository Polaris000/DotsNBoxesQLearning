import engine.*;
import agent.*;
import agent.QL.*;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Bootstrapper
{
    private static Bootstrapper instance = new Bootstrapper();

    private static Game game;

    public void startGame(Game game)
    {
        System.out.println("Creating game simulator.");
        game.run();
    }

    public static Bootstrapper getInstance()
    {
        return instance;
    }

    public static void printGameInfo(int numRounds, int interval, int size, List<String> agentParams)
    {
        // num rounds
        System.out.print(numRounds + " ");
        // results interval
        System.out.print(interval + " ");
        // grid size:
        System.out.print(size + " ");
        // agent 1:
        System.out.print(agentParams.get(0) + " ");

        // agent 2:
        System.out.println(agentParams.get(1) + " ");
        
    }

    public static void main(String[] args)
    {
        int num_rounds = 2000;
        List<String> agentParams = new ArrayList<String>();
        List<String> filenames = new ArrayList<String>();
        List<Agent> agents = new ArrayList<Agent>();
        List<Float> lrs = new ArrayList<Float>();
        List<Float> discounts = new ArrayList<Float>();
        List<Float> epsilons = new ArrayList<Float>();
        
        int size = Grid.default_size;
        int interval = 100;

        try
        {
            size = Integer.parseInt(args[0]);
        }

        catch(Exception e)
        {
            ;
        }

        Bootstrapper bootstrapper = Bootstrapper.getInstance();
        Grid grid = new Grid(size);
        String className = "";

        // define agents in agentParams
        // agent 1
        agentParams.add("Random");
        filenames.add("./Q1");
        lrs.add(0.6f);
        discounts.add(0.5f);
        epsilons.add(0.1f);

        // agent 2
        agentParams.add("QLAgent");
        filenames.add("./Q3");
        lrs.add(0.6f);
        discounts.add(0.5f);
        epsilons.add(0.1f);

        for (int i = 0; i < 2; i++)
        {
            Agent agent = null;
            className = agentParams.get(i);

            if (className.equals("QLAgent"))
            {
                StateMatrix matrix = null;

                float learningRate = lrs.get(i);
                float discountFactor = discounts.get(i);
                float epsilon = epsilons.get(i);

                File f = new File(filenames.get(i));
                System.out.println(filenames.get(i));
                
                if (f.exists())
                {
                    try
                    {
                        matrix = StateMatrix.load_table(filenames.get(i));
                        System.out.println("Loaded matrix...");
                    }
                    catch (IOException e)
                    {
                        System.out.println("File not found." + e);
                        return;
                    }
                }

                else
                {
                    try
                    {
                        matrix = StateMatrix.create_table(filenames.get(i), grid.get_max_id() + 1, grid.get_size());
                        System.out.println("Creating matrix...");
                    }

                    catch (OutOfMemoryError e2)
                    {
                        System.out.println("Out of memory. Terminating.");
                        return;
                    }
                }

                agent = new QLAgent(matrix, discountFactor, learningRate, epsilon, filenames.get(i));
            }

            else if (className.equals("Simple"))
            {
                agent = new Simple();
            }

            else if (className.equals("Random"))
            {
                agent = new RandomPlayer();
            }

            agents.add(agent);
        }


        printGameInfo(num_rounds, interval, size, agentParams);

        grid = new Grid(size);
        game = new Game(grid, agents.get(0), agents.get(1), num_rounds, interval);
        bootstrapper.startGame(game);
    }
}
