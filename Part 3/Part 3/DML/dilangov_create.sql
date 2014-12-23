CREATE DATABASE IF NOT EXISTS dilangov;

USE dilangov;

tee log/dilangov_create_log.txt
--
-- Table structure for table `goal_and_player_scores_goals`
--

CREATE TABLE IF NOT EXISTS `goal_and_player_scores_goals` (
  `Match_Number` int(11) NOT NULL,
  `Date_Year` int(11) NOT NULL,
  `Recorded_Time` int(11) NOT NULL,
  `Player_Name` char(50) NOT NULL,
  `Country_Code` char(3) NOT NULL,
  PRIMARY KEY (`Match_Number`,`Date_Year`,`Recorded_Time`),
  KEY `goal_and_player_scores_goals_ibfk_2` (`Player_Name`,`Country_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE IF NOT EXISTS `team` (
  `Country_Code` char(3) NOT NULL,
  `Country_Name` char(30) NOT NULL,
  `Association` char(50) DEFAULT NULL,
  `Points` int(11) NOT NULL,
  `Ranking` int(11) NOT NULL,
  PRIMARY KEY (`Country_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `Country_Code` char(3) NOT NULL,
  `Player_Role` char(20) NOT NULL,
  `Player_Name` char(50) NOT NULL,
  `DOB` date NOT NULL,
  `Jersey_Number` int(11) NOT NULL,
  `Club` char(50) NOT NULL,
  PRIMARY KEY (`Country_Code`,`Player_Name`),
  KEY `Country_Code` (`Country_Code`),
  KEY `Player_Name` (`Player_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE IF NOT EXISTS `matches` (
  `Stadium` char(50) NOT NULL,
  `Match_Number` int(11) NOT NULL,
  `Winner` char(3) DEFAULT NULL,
  `Decision` char(10) NOT NULL,
  `Team_1` char(3) NOT NULL,
  `Team_2` char(3) NOT NULL,
  `Team_1_Score` int(11) NOT NULL,
  `Team_2_Score` int(11) NOT NULL,
  `Date_Day` int(11) NOT NULL,
  `Date_Month` char(5) NOT NULL,
  `Date_Year` int(11) NOT NULL,
  PRIMARY KEY (`Match_Number`,`Date_Year`),
  KEY `Date_Year` (`Date_Year`),
  KEY `Stadium` (`Stadium`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stadium`
--

CREATE TABLE IF NOT EXISTS `stadium` (
  `Stadium` char(50) NOT NULL,
  `Stadium_Address` char(200) DEFAULT NULL,
  PRIMARY KEY (`Stadium`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Table structure for table `world_cup`
--

CREATE TABLE IF NOT EXISTS `world_cup` (
  `Year` int(11) NOT NULL,
  `Host_Country` char(30) NOT NULL,
  `Winner` char(3) NOT NULL,
  `Runner_Up` char(3) NOT NULL,
  PRIMARY KEY (`Year`),
  KEY `Winner` (`Winner`),
  KEY `Runner_Up` (`Runner_Up`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Table structure for table `match_played_by`
--

CREATE TABLE IF NOT EXISTS `match_played_by` (
  `Match_Number` int(11) NOT NULL,
  `Date_Year` int(11) NOT NULL,
  `Player_Name` char(50) NOT NULL,
  `Country_Code` char(3) NOT NULL,
  PRIMARY KEY (`Match_Number`,`Date_Year`,`Player_Name`,`Country_Code`),
  KEY `match_played_by_ibfk_2` (`Player_Name`,`Country_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------


--
-- Table structure for table `team_participates_in_world_cup`
--

CREATE TABLE IF NOT EXISTS `team_participates_in_world_cup` (
  `Groups` char(1) DEFAULT NULL,
  `Country_Code` char(3) NOT NULL,
  `Year` int(11) NOT NULL,
  PRIMARY KEY (`Year`,`Country_Code`),
  KEY `Country_Code` (`Country_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `world_cup_played_by_player`
--

CREATE TABLE IF NOT EXISTS `world_cup_played_by_player` (
  `Year` int(11) NOT NULL,
  `Player_Name` char(50) NOT NULL,
  `Country_Code` char(3) NOT NULL,
  PRIMARY KEY (`Year`,`Player_Name`,`Country_Code`),
  KEY `world_cup_played_by_player_ibfk_2` (`Player_Name`,`Country_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Constraints for dumped tables
--

--
-- Constraints for table `goal_and_player_scores_goals`
--
ALTER TABLE `goal_and_player_scores_goals`
  ADD CONSTRAINT `goal_and_player_scores_goals_ibfk_2` FOREIGN KEY (`Player_Name`, `Country_Code`) REFERENCES `player` (`Player_Name`, `Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE ,
  ADD CONSTRAINT `goal_and_player_scores_goals_ibfk_1` FOREIGN KEY (`Match_Number`, `Date_Year`) REFERENCES `matches` (`Match_Number`, `Date_Year`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `matches_ibfk_1` FOREIGN KEY (`Date_Year`) REFERENCES `world_cup` (`Year`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `matches_ibfk_2` FOREIGN KEY (`Stadium`) REFERENCES `stadium` (`Stadium`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `matches_ibfk_3` FOREIGN KEY (`Team_1`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `matches_ibfk_4` FOREIGN KEY (`Team_2`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `match_played_by`
--
ALTER TABLE `match_played_by`
  ADD CONSTRAINT `match_played_by_ibfk_1` FOREIGN KEY (`Match_Number`, `Date_Year`) REFERENCES `matches` (`Match_Number`, `Date_Year`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `match_played_by_ibfk_2` FOREIGN KEY (`Player_Name`, `Country_Code`) REFERENCES `player` (`Player_Name`, `Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`Country_Code`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `team_participates_in_world_cup`
--
ALTER TABLE `team_participates_in_world_cup`
  ADD CONSTRAINT `team_participates_in_world_cup_ibfk_1` FOREIGN KEY (`Year`) REFERENCES `world_cup` (`Year`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `team_participates_in_world_cup_ibfk_2` FOREIGN KEY (`Country_Code`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `world_cup`
--
ALTER TABLE `world_cup`
  ADD CONSTRAINT `world_cup_ibfk_1` FOREIGN KEY (`Winner`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `world_cup_ibfk_2` FOREIGN KEY (`Runner_Up`) REFERENCES `team` (`Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `world_cup_played_by_player`
--
ALTER TABLE `world_cup_played_by_player`
  ADD CONSTRAINT `world_cup_played_by_player_ibfk_1` FOREIGN KEY (`Year`) REFERENCES `world_cup` (`Year`) ON UPDATE CASCADE ON DELETE CASCADE,
  ADD CONSTRAINT `world_cup_played_by_player_ibfk_2` FOREIGN KEY (`Player_Name`, `Country_Code`) REFERENCES `player` (`Player_Name`, `Country_Code`) ON UPDATE CASCADE ON DELETE CASCADE;

