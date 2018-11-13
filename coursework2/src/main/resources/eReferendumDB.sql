--
-- Table structure for table `BIC_RECORD`
--

DROP TABLE IF EXISTS `BIC_RECORD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BIC_RECORD` (
  `BIC` varchar(30) NOT NULL,
  `USED` int(11) DEFAULT NULL,
  PRIMARY KEY (`BIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BIC_RECORD`
--

LOCK TABLES `BIC_RECORD` WRITE;
/*!40000 ALTER TABLE `BIC_RECORD` DISABLE KEYS */;
INSERT INTO `BIC_RECORD` VALUES ('7F63-YDNH-G2LL-AKY7',0),('7M73-LMDA-883S-EJT7 ',0),('9T5C-RD3T-RYF2-SSJM',0),('DJL8-3RDP-32JS-QUA8',0),('E4U4-Z87Z-G6BM-QEJM',0),('EHNL-G55E-YMQD-GF25',0),('GJ2X-ERGA-RGT7-D9V9',0),('TGJ7-CHX4-8FXA-35WF',0),('VLPF-MXSF-533T-BA5Y ',0),('ZCXT-G275-JJ3R-C5YU',0);
/*!40000 ALTER TABLE `BIC_RECORD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REFERENDUM`
--

DROP TABLE IF EXISTS `REFERENDUM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REFERENDUM` (
  `REF_ID` int(11) NOT NULL,
  `REFERENDUM_TITLE` varchar(200) NOT NULL,
  `OPEN` int(11) DEFAULT NULL,
  PRIMARY KEY (`REF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REFERENDUM`
--

LOCK TABLES `REFERENDUM` WRITE;
/*!40000 ALTER TABLE `REFERENDUM` DISABLE KEYS */;
INSERT INTO `REFERENDUM` VALUES (1,'How should Shangri-La proceed to the next stage? (choose ONE)',1);
/*!40000 ALTER TABLE `REFERENDUM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REFERENDUM_OPTIONS`
--

DROP TABLE IF EXISTS `REFERENDUM_OPTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REFERENDUM_OPTIONS` (
  `OPT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REF_ID` int(11) NOT NULL,
  `OPTION` varchar(45) DEFAULT NULL,
  `VOTE_COUNT` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`OPT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REFERENDUM_OPTIONS`
--

LOCK TABLES `REFERENDUM_OPTIONS` WRITE;
/*!40000 ALTER TABLE `REFERENDUM_OPTIONS` DISABLE KEYS */;
INSERT INTO `REFERENDUM_OPTIONS` VALUES (1,1,'No deal',0),(2,1,'Government\'s proposed trade deal',0),(3,1,'Status quo',0);
/*!40000 ALTER TABLE `REFERENDUM_OPTIONS` ENABLE KEYS */;
UNLOCK TABLES;
