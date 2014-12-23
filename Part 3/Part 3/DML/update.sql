use dilangov;
/*Delete query 1
Delete player participation (world_cup_played_by_player) records of players who play for club Santos but have not scored any goal in the world cups held so far*/
DELETE FROM world_cup_played_by_player WHERE (Player_Name, Country_Code) IN (SELECT distinct Player_Name, Country_Code FROM player WHERE Club like 'Santos%' AND (Player_Name, Country_Code) NOT IN (SELECT distinct Player_Name,Country_Code FROM goal_and_player_scores_goals));
/*We find all players (uniquely identified by Player_Name and Country_Code). We select players who have scored any goal in any of world cup matches by selecting from goal_and_player_scores_goals. We select players who belong to club Santos. We select all players who have particpated in world cup from world_cup_played_by_player. We filter those who are from club Santos but exclude those who have scored any goal (meaning do not filter those who have not scored any goal). We now delete this unique list of player participation from world_cup_played_by_player.*/

/*Delete query 2
Delete from the team participation (team_participates_in_world_cup) if the country has not participated in the world cup since 1990 and its total tally of points is 0*/
delete FROM team_participates_in_world_cup WHERE Country_Code in ( select Country_Code FROM team t where t.Country_Code=Country_Code and t.Points=0) and Year>1990;
/*For each team that participated in the world cup, we check if its overall points is 0 by referencing team table on Country_Code. We include only those team that have participated in any world cup after 1990. We delete the selected teams from team_participates_in_world_cup.*/

/*Insert query 1,2,3
The below set of queries introduce a new world cup 2018

World cup 2018 in which Brazil hosted, Brazil was winner and Spain was runner up*/
INSERT INTO world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (2018, 'Brazil', 'BR', 'ES');

/*World cup 2018 in which the teams that had ranking <=50 participated*/
INSERT INTO team_participates_in_world_cup (Country_Code, Year)
SELECT x.Country_Code, 2018 
FROM team x, world_cup y WHERE x.Ranking<=50 AND y.Year=2014;

/*World cup 2018 in which the players who were aged >=25 and who had scored atleast 2 goals in world cup record.*/
INSERT INTO world_cup_played_by_player 
SELECT x.Year, y.Player_Name, y.Country_Code 
FROM world_cup x, player y 
WHERE x.Year=2018 AND 2018-YEAR(y.DOB)>= 25 AND y.Player_Name IN (SELECT Player_Name FROM goal_and_player_scores_goals GROUP BY Player_Name HAVING COUNT(*) > 2);

/*Update query 1
Updates the ranking of the team from the scale 1-100 (lower is better) instead of 100-1. Sets the rank of a team to -1 if it has not scored in any world cup after 2000 (Idea is that the rankings of these teams are not to be considered valid if they have not been participating/performing well). Team performance is identified if they have scored */

UPDATE team 
SET Ranking = case when (team.Country_Code NOT IN (SELECT Country_Code FROM goal_and_player_scores_goals as gp WHERE gp.Date_Year > 2000)) then -1 when team.Points != 0 then 100 - Ranking end;
	
/*Update query 2*/
update team set Ranking = (select count(*) from goal_and_player_scores_goals g where g.Date_Year = 2014 and Country_Code = g.Country_Code);

use dilangov-small;
/*Delete query 1
Delete player participation (world_cup_played_by_player) records of players who play for club Santos but have not scored any goal in the world cups held so far*/
DELETE FROM world_cup_played_by_player WHERE (Player_Name, Country_Code) IN (SELECT distinct Player_Name, Country_Code FROM player WHERE Club like 'Santos%' AND (Player_Name, Country_Code) NOT IN (SELECT distinct Player_Name,Country_Code FROM goal_and_player_scores_goals));
/*We find all players (uniquely identified by Player_Name and Country_Code). We select players who have scored any goal in any of world cup matches by selecting from goal_and_player_scores_goals. We select players who belong to club Santos. We select all players who have particpated in world cup from world_cup_played_by_player. We filter those who are from club Santos but exclude those who have scored any goal (meaning do not filter those who have not scored any goal). We now delete this unique list of player participation from world_cup_played_by_player.*/

/*Delete query 2
Delete from the team participation (team_participates_in_world_cup) if the country has not participated in the world cup since 1990 and its total tally of points is 0*/
delete FROM team_participates_in_world_cup WHERE Country_Code in ( select Country_Code FROM team t where t.Country_Code=Country_Code and t.Points=0) and Year>1990;
/*For each team that participated in the world cup, we check if its overall points is 0 by referencing team table on Country_Code. We include only those team that have participated in any world cup after 1990. We delete the selected teams from team_participates_in_world_cup.*/

/*Update query 1
Updates the ranking of the team from the scale 1-100 (lower is better) instead of 100-1. Sets the rank of a team to -1 if it has not scored in any world cup after 2000 (Idea is that the rankings of these teams are not to be considered valid if they have not been participating/performing well). Team performance is identified if they have scored */

UPDATE team 
SET Ranking = case when (team.Country_Code NOT IN (SELECT Country_Code FROM goal_and_player_scores_goals as gp WHERE gp.Date_Year > 2000)) then -1 when team.Points != 0 then 100 - Ranking end;
	
/*Update query 2*/
update team set Ranking = (select count(*) from goal_and_player_scores_goals g where g.Date_Year = 2014 and Country_Code = g.Country_Code);
	
/*Insert query 1,2,3
The below set of queries introduce a new world cup 2018

World cup 2018 in which Brazil hosted, Brazil was winner and Spain was runner up*/
INSERT INTO world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (2018, 'Brazil', 'BR', 'ES');

/*World cup 2018 in which the teams that had ranking <=50 participated*/
INSERT INTO team_participates_in_world_cup (Country_Code, Year)
SELECT x.Country_Code, 2018 
FROM team x, world_cup y WHERE x.Ranking<=50 AND y.Year=2014;

/*World cup 2018 in which the players who were aged >=25 and who had scored atleast 2 goals in world cup record.*/
INSERT INTO world_cup_played_by_player 
SELECT x.Year, y.Player_Name, y.Country_Code 
FROM world_cup x, player y 
WHERE x.Year=2018 AND 2018-YEAR(y.DOB)>= 25 AND y.Player_Name IN (SELECT Player_Name FROM goal_and_player_scores_goals GROUP BY Player_Name HAVING COUNT(*) > 2);




