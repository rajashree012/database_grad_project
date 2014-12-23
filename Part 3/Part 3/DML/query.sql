use dilangov;
/*Select query 1
List of players who were part of the squad of countries which belonged to group A in 2014 edition of world cup. The table shows even the country code the player is part of.*/
select Player_Name,wp.Country_Code from world_cup_played_by_player wp , team_participates_in_world_cup tw where wp.Year=tw.Year and tw.Groups='A' and tw.Year=2014 and tw.Country_Code=wp.Country_Code order by wp.Country_Code;

/*Select query 2
List of players belonging to country Spain and who were also part of Barcelona club and who played world cup matches between 2002 and 2014 (both included). Players roles are showed in the output table.*/
select distinct p.Player_Name,p.Player_Role  from world_cup_played_by_player wp, player p where wp.Player_Name=p.Player_Name and wp.Country_Code=p.Country_Code and p.Country_Code='ES' and wp.Year >=2002 and wp.Year <=2014 and p.Club like 'Barcelona%';

/*Select query 3
List of matches of all the editions of world cup during which at least one goal is scored after normal duration of game (>90) and also where Argentina emerged as winner of that match. Both the participating countries and the year in which match took place are shown in the output table.*/
 select distinct m.Match_Number, m.Date_Year, Team_1, Team_2
     from goal_and_player_scores_goals pg, matches m
     where m.Match_Number=pg.Match_Number
	 and m.Date_Year=pg.Date_Year
     and Recorded_Time>90
     and m.Winner='AR';

/*Join query 1	 
List of players who are defenders and who scored at least one goal in the first 45 minutes of the game and also only those matches are considered where his team emerged as winner. The country_code of the player is also shown in the output table.*/
select Player_Name,Country_Code, Match_Number  from (player p join matches m )natural join goal_and_player_scores_goals gp  where gp.Recorded_Time < 45 and p.Country_Code = m.Winner and p.Player_Role = 'DF' and m.Date_Year =2014;

/*Join query 2
The query finds the stadium in which maximum number of goals were scored for each edition of the world cup.*/
select Stadium,Date_Year, Stadium_Address from (select Stadium,Date_Year,count(*) as goals   from matches natural join goal_and_player_scores_goals   group by Date_Year,Stadium)r1 natural join stadium  where goals =(select max(temp1.x) from (select Stadium,Date_Year,count(*) as x from (matches natural join goal_and_player_scores_goals) group by Date_Year,Stadium)temp1 where Date_Year=r1.Date_Year group by Date_Year);

/*Union query
The query finds all match info (stadium, match date,team 1, team 2, team scores and the winner of the match) of matches of the year in which brazil was world cup winner or runner up and played in the match as team 1 or team2. We perform the select for year when brazil was winner and union it with results when brazil was runner up.*/
(select Stadium,m.Date_Day,m.Date_Month,  m.Date_Year, Team_1, Team_2, Team_1_Score, Team_2_Score, m.Winner, w.Winner as World_Cup_Winner, w.Runner_Up as World_Cup_Runner_Up from matches m, world_cup w where w.Winner='BR' and w.Year=m.Date_Year and (w.Winner=m.Team_1 or w.Winner=m.Team_2))
     union  
    (select Stadium,m.Date_Day,m.Date_Month,  m.Date_Year, Team_1, Team_2, Team_1_Score, Team_2_Score, m.Winner, w.Winner as World_Cup_Winner, w.Runner_Up as World_Cup_Runner_Up from matches m, world_cup w where w.Runner_Up='BR' and w.Year=m.Date_Year and (w.Runner_Up=m.Team_1 or w.Runner_Up=m.Team_2));
	
/*Group by query
We find player who has world record based on his goal history in all years. If multiple players have same record all are returned. We group goal_and_player_scores_goals by player (Player_Name and Country_Code). We thus find count of all goals scored by the player. We find max of all total goals by each player. We finally select all players whose total goals matches the max of all total goals by all players.*/
select Player_Name, Country_Code from goal_and_player_scores_goals group by Player_Name, Country_Code having  count(*) = (select max(x.Total_Goals) from (select count(*) as Total_Goals from goal_and_player_scores_goals group by Player_Name, Country_Code) x);

/*Order by query
We display list of matches ordered in descending order of goal difference. We exclude goals scored by penalty, i.e >120 minutes of the game. We obtain goal information for each match with join on goal_and_player_scores_goals. We display top 20 matches that were lost by major difference.*/
select Date_Year, Date_Month, Date_Day, Stadium, Team_1, Team_2, Winner, abs(Team_1_Score-Team_2_Score) as Goal_Difference from matches natural join (select distinct Match_Number, Date_Year from goal_and_player_scores_goals where Recorded_Time<=120) filtered_matches order by Goal_Difference desc limit 20;

/*Distinct query
List players who have played in a world cup when they were aged less 25 and belonged to club Santos or Flamengo*/
select distinct Player_Name, Country_Code from player p natural join world_cup_played_by_player wp where Year-Year(DOB)<25 and Club in ('Santos','Flamengo');

/*We find all players (uniquely identified by Player_Name and Country_Code). We find players who have participated in world cup by joining with world_cup_played_by_player. We now check the age of those players in each world_cup they played by computing difference of year of world_cup and thier year of date of birth. We select players aged less than 25. We also filter only those players whose club ins Santos or Flamengo. Now each player could have participated in multiple world cup. So our end result will have players repeated. Hence we select distinct players.*/

/*Aggregate query
List of countries which have participated in all the football world cups held since 1930*/
select Country_Code from team_participates_in_world_cup group by Country_Code having count(*) = (select count(Year) from world_cup);
/*We first select the count of years world cups were conducted from world_cup table. We form groups of world_cups for each team and check if count of world cup matches count of years world cups were conducted (using having clause)*/

use dilangov-small;
/*Select query 1
List of players who were part of the squad of countries which belonged to group A in 2014 edition of world cup. The table shows even the country code the player is part of.*/
select Player_Name,wp.Country_Code from world_cup_played_by_player wp , team_participates_in_world_cup tw where wp.Year=tw.Year and tw.Groups='A' and tw.Year=2014 and tw.Country_Code=wp.Country_Code order by wp.Country_Code;

/*Select query 2
List of players belonging to country Spain and who were also part of Barcelona club and who played world cup matches between 2002 and 2014 (both included). Players roles are showed in the output table.*/
select distinct p.Player_Name,p.Player_Role  from world_cup_played_by_player wp, player p where wp.Player_Name=p.Player_Name and wp.Country_Code=p.Country_Code and p.Country_Code='ES' and wp.Year >=2002 and wp.Year <=2014 and p.Club like 'Barcelona%';

/*Select query 3
List of matches of all the editions of world cup during which at least one goal is scored after normal duration of game (>90) and also where Argentina emerged as winner of that match. Both the participating countries and the year in which match took place are shown in the output table.*/
 select distinct m.Match_Number, m.Date_Year, Team_1, Team_2
     from goal_and_player_scores_goals pg, matches m
     where m.Match_Number=pg.Match_Number
	 and m.Date_Year=pg.Date_Year
     and Recorded_Time>90
     and m.Winner='AR';

/*Join query 1	 
List of players who are defenders and who scored at least one goal in the first 45 minutes of the game and also only those matches are considered where his team emerged as winner. The country_code of the player is also shown in the output table.*/
select Player_Name,Country_Code, Match_Number  from (player p join matches m )natural join goal_and_player_scores_goals gp  where gp.Recorded_Time < 45 and p.Country_Code = m.Winner and p.Player_Role = 'DF' and m.Date_Year =2014;

/*Join query 2
The query finds the stadium in which maximum number of goals were scored for each edition of the world cup.*/
select Stadium,Date_Year, Stadium_Address from (select Stadium,Date_Year,count(*) as goals   from matches natural join goal_and_player_scores_goals   group by Date_Year,Stadium)r1 natural join stadium  where goals =(select max(temp1.x) from (select Stadium,Date_Year,count(*) as x from (matches natural join goal_and_player_scores_goals) group by Date_Year,Stadium)temp1 where Date_Year=r1.Date_Year group by Date_Year);

/*Union query
The query finds all match info (stadium, match date,team 1, team 2, team scores and the winner of the match) of matches of the year in which Germany was world cup winner or runner up and played in the match as team 1 or team2. We perform the select for year when Germany was winner and union it with results when Germany was runner up.*/
(select Stadium,m.Date_Day,m.Date_Month,  m.Date_Year, Team_1, Team_2, Team_1_Score, Team_2_Score, m.Winner, w.Winner as World_Cup_Winner, w.Runner_Up as World_Cup_Runner_Up from matches m, world_cup w where w.Winner='DE' and w.Year=m.Date_Year and (w.Winner=m.Team_1 or w.Winner=m.Team_2))
     union  
    (select Stadium,m.Date_Day,m.Date_Month,  m.Date_Year, Team_1, Team_2, Team_1_Score, Team_2_Score, m.Winner, w.Winner as World_Cup_Winner, w.Runner_Up as World_Cup_Runner_Up from matches m, world_cup w where w.Runner_Up='DE' and w.Year=m.Date_Year and (w.Runner_Up=m.Team_1 or w.Runner_Up=m.Team_2));
	
/*Group by query
We find player who has world record based on his goal history in all years. If multiple players have same record all are returned. We group goal_and_player_scores_goals by player (Player_Name and Country_Code). We thus find count of all goals scored by the player. We find max of all total goals by each player. We finally select all players whose total goals matches the max of all total goals by all players.*/
select Player_Name, Country_Code from goal_and_player_scores_goals group by Player_Name, Country_Code having  count(*) = (select max(x.Total_Goals) from (select count(*) as Total_Goals from goal_and_player_scores_goals group by Player_Name, Country_Code) x);

/*Order by query
We display list of matches ordered in descending order of goal difference. We exclude goals scored by penalty, i.e >120 minutes of the game. We obtain goal information for each match with join on goal_and_player_scores_goals. We display top 20 matches that were lost by major difference.*/
select Date_Year, Date_Month, Date_Day, Stadium, Team_1, Team_2, Winner, abs(Team_1_Score-Team_2_Score) as Goal_Difference from matches natural join (select distinct Match_Number, Date_Year from goal_and_player_scores_goals where Recorded_Time<=120) filtered_matches order by Goal_Difference desc limit 20;

/*Distinct query
List players who have played in a world cup when they were aged less 25 and belonged to club Santos or Flamengo*/
select distinct Player_Name, Country_Code from player p natural join world_cup_played_by_player wp where Year-Year(DOB)<25 and Club in ('Santos','Flamengo');

/*We find all players (uniquely identified by Player_Name and Country_Code). We find players who have participated in world cup by joining with world_cup_played_by_player. We now check the age of those players in each world_cup they played by computing difference of year of world_cup and thier year of date of birth. We select players aged less than 25. We also filter only those players whose club ins Santos or Flamengo. Now each player could have participated in multiple world cup. So our end result will have players repeated. Hence we select distinct players.*/

/*Aggregate query
List of countries which have participated in all the football world cups held since 1930*/
select Country_Code from team_participates_in_world_cup group by Country_Code having count(*) = (select count(Year) from world_cup);
/*We first select the count of years world cups were conducted from world_cup table. We form groups of world_cups for each team and check if count of world cup matches count of years world cups were conducted (using having clause)*/





