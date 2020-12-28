package engine;

import java.util.ArrayList;
import java.util.List;

import agent.*;

public class ScoreTable
{
    private class ScoreEntry
    {

        Agent agent;
        int score;
        int boxes;

        public ScoreEntry(Agent agent)
        {
            this.agent = agent;
            score = 0;
            boxes = 0;
        }
    }

    List<ScoreEntry> table;


    public ScoreTable()
    {
        table = new ArrayList<ScoreEntry>();
    }

    public void reset()
    {
        for (ScoreEntry entry : table)
        {
            entry.score = 0;
            entry.boxes = 0;
        }
    }

    public void reset_boxes()
    {
        for (ScoreEntry entry : table)
            entry.boxes = 0;
    }

    public void add_agent_entry(Agent agent)
    {
        for (ScoreEntry entry : table)
            if (entry.agent == agent)
                return;

        table.add(new ScoreEntry(agent));
    }

    public void inc_score(Agent agent)
    {
        ScoreEntry target = null;

        for (ScoreEntry entry : table)
        {
            if (entry.agent == agent)
            {
                target = entry;
                break;
            }
        }

        if (target == null)
        {
            target = new ScoreEntry(agent);
            table.add(target);
        }

        ++target.score;
    }

    public void increment_complete_boxes(Agent agent, int num)
    {
        ScoreEntry target = null;

        for (ScoreEntry entry : table)
        {
            if (entry.agent == agent)
            {
                entry.boxes += num;
                break;
            }
        }

        if (target == null)
        {
            target = new ScoreEntry(agent);
            table.add(target);
        }

        target.boxes += num;
    }


    public int get_score(Agent agent)
    {
        for (ScoreEntry entry : table)
            if (entry.agent == agent)
                return entry.score;
        return -1;
    }


    public List<Agent> winner()
    {
        List<Agent> agents = new ArrayList<Agent>();
        int max = Integer.MIN_VALUE;

        for (ScoreEntry entry : table)
        {
            if (entry.boxes > max)
            {
                agents.clear();
                agents.add(entry.agent);
                max = entry.boxes;
            }
            else if (entry.boxes == max)
            {
                agents.add(entry.agent);
            }
        }

        return agents;
    }


    public int completed_boxes(Agent agent)
    {
        for (ScoreEntry entry : table)
            if (entry.agent == agent)
                return entry.boxes;
        return -1;
    }
}
