insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('BR','brazil','CONMEBOL',216,97);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('DE','deutschland','UEFA',199,99);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('IT','italy','UEFA',153,80);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('AR','argentina','CONMEBOL',124,70);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('EN','england','UEFA',97,59);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('ES','espana','UEFA',96,56);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('FR','france','UEFA',86,54);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('NL','netherlands','UEFA',76,43);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('UY','uruguay','CONMEBOL',66,47);
insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('SE','sweden','UEFA',61,46);

INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1986, "Mexico", "AR", "FRG");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1990, "Italy", "FRG", "AR");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1982, "Spain", "IT", "FRG");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1994, "United States", "BR", "IT");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1978, "Argentina", "AR", "NL");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1998, "France", "FR", "BR");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1966, "England", "EN", "FRG");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1974, "West Germany", "FRG", "NL");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1962, "Chile", "BR", "TCH");
INSERT INTO  world_cup (Year, Host_Country, Winner, Runner_Up) VALUES (1970, "Mexico", "BR", "IT");

INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("UY", "FW", "lvaro Recoba", 1979-02-17, 20, "Internazionale");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("AT", "DF", "Peter Schttel", 1976-09-22, 3, "Rapid Wien");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("IT", "FW", "Humberto Maschio", 1933-02-02, 8, "Atalanta");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("PY", "DF", "Alberto Gonzlez", 1929-09-27, 22, "Olimpia");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("PE", "DF", "Rodolfo Manzo", 1950-11-19, 3, "Deportivo Municipal");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("SN", "DF", "Papa Malick Diop", 1977-05-22, 4, "Lorient");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("BG", "MF", "Yordan Letchkov", 1972-06-04, 9, "Hamburger SV");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("US", "DF", "DeAndre Yedlin", 1993-03-12, 2, "Seattle Sounders FC (USA)");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("DE", "FW", "Leopold Neumer", 1914-06-16, 18, "Austria Wien");
INSERT INTO player (Country_Code, Player_Role, Player_Name, DOB, Jersey_Number, Club) VALUES ("FR", "MF", "Augustin Chantrel", 1900-05-08, 28, "CASG Paris");

insert into stadium (Stadium, Stadium_Address) values (' estadio pocitos',' estadio pocitos, montevideo');
insert into stadium (Stadium, Stadium_Address) values (' estadio parque central',' estadio parque central, montevideo');
insert into stadium (Stadium, Stadium_Address) values (' estadio centenario',' estadio centenario, montevideo');
insert into stadium (Stadium, Stadium_Address) values (' stadio littoriale',' stadio littoriale, bologna');
insert into stadium (Stadium, Stadium_Address) values (' stadio benito mussolini',' stadio benito mussolini, turin');
insert into stadium (Stadium, Stadium_Address) values (' stadio giovanni berta',' stadio giovanni berta, florence');
insert into stadium (Stadium, Stadium_Address) values (' stadio luigi ferraris',' stadio luigi ferraris, genoa');
insert into stadium (Stadium, Stadium_Address) values (' stadio giorgio ascarelli',' stadio giorgio ascarelli, naples');
insert into stadium (Stadium, Stadium_Address) values (' stadio san siro',' stadio san siro, milan');
insert into stadium (Stadium, Stadium_Address) values (' stadio nazionale pnf',' stadio nazionale pnf, rome');

insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio pocitos',1,'FR','WINNER','FR','MX',4,1,13,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio parque central',5,'AR','WINNER','AR','FR',1,0,15,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio parque central',6,'CL','WINNER','CL','MX',3,0,16,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio centenario',10,'CL','WINNER','CL','FR',1,0,19,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio centenario',11,'AR','WINNER','AR','MX',6,3,19,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio centenario',15,'AR','WINNER','AR','CL',3,1,22,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio parque central',3,'YUG','WINNER','YUG','BR',2,1,14,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio parque central',7,'YUG','WINNER','YUG','BO',4,0,17,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio centenario',12,'BR','WINNER','BR','BO',4,0,20,'July',1930);
insert into matches (Stadium, Match_Number, Winner, Decision, Team_1, Team_2, Team_1_Score, Team_2_Score, Date_Day, Date_Month, Date_Year) values (' estadio pocitos',4,'RO','WINNER','RO','PE',3,1,14,'July',1930);

INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Luis Islas", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Nery Pumpido", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Hctor Zelada", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Jos Luis Brown", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Daniel Passarella", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Nstor Clausen", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Jos Luis Cuciuffo", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Oscar Garr", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Julio Olarticoechea", "AR");
INSERT INTO world_cup_played_by_player (Year, Player_Name, Country_Code) VALUES (1986, "Oscar Ruggeri", "AR");


insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Lucien Laurent');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','mile Veinante');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Augustin Chantrel');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Jean Laurent');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Marcel Langiller');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Andr Maschinot');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','tienne Mattler');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Edmond Delfour');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Ernest Librati');
insert into match_played_by (Match_Number, Date_Year, Country_Code, Player_Name) values (1,1930,'FR','Alexandre Villaplane');

insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (1,1930,115,'Numa Andoire','FR');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (1,1930,76,'Clestin Delmer','FR');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (1,1930,95,'mile Veinante','FR');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (1,1930,6,'Alexandre Villaplane','FR');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (1,1930,3,'Felipe Olivares','MX');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (5,1930,19,'Alejandro Scopelli','AR');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (6,1930,36,'Guillermo Saavedra','CL');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (6,1930,42,'Arturo Coddou','CL');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (6,1930,115,'Guillermo Saavedra','CL');
insert into goal_and_player_scores_goals (Match_Number,Date_Year,Recorded_Time,Player_Name,Country_Code) values (10,1930,10,'Guillermo Arellano','CL');

INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "PE", 1978);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "NL", 1978);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "SCO", 1978);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "IR", 1978);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "EN", 1954);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "CH", 1954);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "IT", 1954);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("4", "BE", 1954);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("G", "CH", 2006);
INSERT INTO team_participates_in_world_cup (Groups, Country_Code, Year) VALUES ("G", "FR", 2006);
