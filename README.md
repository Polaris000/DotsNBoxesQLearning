# DotsNBoxesQLearning

## Abstract
Reinforcement learning is an area of machine learning that is concerned with algorithms for training agents to perform some task within their environments. RL algorithms have a concept of rewards for agents. The agents are given rewards depending upon their performance on their tasks and the RL algorithms teach the agents to take actions that maximize overall reward. Q-Learning is a reinforcement learning algorithm that teaches agents to learn a value for every possible action in every state. The possible configurations of the agent and the environment are the states for the algorithm. Once the agent is properly trained, the action with the maximum Q-value at each state will lead the agent to the most optimal outcome. 

Q-Learning is parameterized by three main parameters which affect how well the trained agent performs. The learning rate determines how much weight is assigned to new information received when performing a new iteration vs the old information from all the previous iterations. If this parameter is too high, there will be too much fluctuation in the Q-values with every iteration and the training will be unstable. If it is too low, the agent will take too long to learn proper Q-values. 

The second parameter, discount factor, determines how much weight is given to future rewards as compared to immediate rewards. If the discount factor is low, the agent will more strongly favour immediate rewards while a high discount factor favours high rewards in the future. 

The third parameter epsilon controls the degree of exploration. As the agent is being trained some actions may initially appear to lead to a high reward. If the agent always picks the action with the best Q-value, during training, it will not be able to visit all state-action pairs. The epsilon factor allows the agent to “explore” during training time by picking an action which appears to be sub-optimal in the hope that it will lead to high rewards in the future. A high epsilon means that the agent will perform too much exploration, leading to slower convergence while a low epsilon leads to low exploration which can result in actually optimal actions for each state not being found.


## The DotsNBoxes Game
we use Q-Learning to train an agent to play the game dots and boxes. This game consists of a board with a grid of dots. During each round a player connects two dots with a horizontal or vertical line. If a player draws a line that closes a box, they get a point. The player with the maximum number of points wins. For this game, every board configuration is a state for the Q-Learning algorithm and actions consist of drawing horizontal or vertical lines between available dots.


## Project Implementation
The general idea of the project is simple: implement a naive user-vased recommendation system and build upon that to get better results.

- Naive
User based collaborative filtering works based on the assumption that the users who have liked
similar movies in the past will tend to like similar movies in the future. Hence, the first step in
recommending movies to target users is to find similar neighbours of the given target user. The
similarity metric used in our study is Pearson Correlation. After calculating the similarity score of
target user with every other user, we consider top 10 most similar users to the target users in our
further calculations. Predictions are made using the Resnick prediction formula.

- Improvements made:
    - Using tag data (unlike the naive approach, which used only rating data)
    - Giving importance to the number of co-rated movies
    - Increasing the number of neighbours
    - Giving more importance to rare movies (with a lesser number of movies)
    - Using cosine similarity instead of Pearson Correlation
    
- Testing: MAE loss was used along with 5-fold cross-validation.

    
    
## Results
- Naive Implementation  
![](/results/naive.png)

- Improvement 1:  
![](/results/imp1.png)

- Improvement 2:  
![](/results/imp2.png)

- Improvement 3:  
![](/results/imp3.png)

- Improvement 4:  
![](/results/imp4.png)

- Improvement 5:  
![](/results/imp5.png)

## Usage

### Libraries

```
numpy==1.19.4
pandas==1.1.4
scipy==1.5.4
prettytable==2.0.0
vaderSentiment==3.3.2
tqdm==4.51.0
```

To install libraries:  
```
$ pip3 install -r requirements.txt
```

### Data
MovieLens Dataset
#### Files
- ratings.csv
- tags.csv
- movies.csv
- test_user.txt: random users to make predictions on

### Input Files
--------
- to RS_main.py:  
    - ratings.csv  
    - tags.csv  

- to test.py:  
    - test_user.txt    
    - utilitymatrix2.csv (generated from RS_main.py)  

### Execution  
RS_main.py:  

Running this file executes the recommender system including prediction and and performance evaluation for the basic implementation and its 5 improvements.   

    $ python3 RS_main.py --input ./data/ratings.csv --output eval.csv


test.py:  
  
Executing this file lists the top-5 recommended movies along with previously seen movies for the 10 random users using our best performing improvement to the recommender system.  
  
    $ python3 test.py --input ./data/test_user.txt --output output.csv

### Output files

- RS_main.py:  
  - utilitymatrix.csv  
  - utilitymatrix2.csv (main utility matrix)  
  - eval.csv: MAE values for each implementation
  
- test.py:  
    - output.csv: final predictions
    
## Acknowledgements
This project is a team effort. Contributions were made by:
- Aniruddha Karajgi
- Atmadeep Banerjee


For more information regarding this project, have a look at our [report](Report.pdf).
  
  
