/*
Navicat MySQL Data Transfer

Source Server         : localbd
Source Server Version : 50625
Source Host           : 127.0.0.1:3306
Source Database       : viajes

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2018-10-17 14:45:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for autos
-- ----------------------------
DROP TABLE IF EXISTS `autos`;
CREATE TABLE `autos` (
  `AUT_PLACA` char(7) NOT NULL,
  `MOD_CODIGO` char(2) DEFAULT NULL,
  `AUT_ANIO` char(4) DEFAULT NULL,
  `AUT_COLOR` varchar(20) DEFAULT NULL,
  `AUT_CAPACIDAD` int(11) DEFAULT NULL,
  `AUT_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AUT_PLACA`),
  KEY `FK_MODELO_AUTO` (`MOD_CODIGO`),
  CONSTRAINT `FK_MODELO_AUTO` FOREIGN KEY (`MOD_CODIGO`) REFERENCES `modelo` (`MOD_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autos
-- ----------------------------
INSERT INTO `autos` VALUES ('000', '01', '1999', 'Verde', '2', 'NADA');
INSERT INTO `autos` VALUES ('0001', '01', '2000', 'Blanco', '5', 'NADA');

-- ----------------------------
-- Table structure for ciudades
-- ----------------------------
DROP TABLE IF EXISTS `ciudades`;
CREATE TABLE `ciudades` (
  `CIU_CODIGO` char(2) NOT NULL,
  `CIU_NOMBRE` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`CIU_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ciudades
-- ----------------------------

-- ----------------------------
-- Table structure for marca
-- ----------------------------
DROP TABLE IF EXISTS `marca`;
CREATE TABLE `marca` (
  `MAR_CODIGO` char(2) NOT NULL,
  `MAR_NOMBRE` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`MAR_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of marca
-- ----------------------------
INSERT INTO `marca` VALUES ('01', 'FORD');
INSERT INTO `marca` VALUES ('02', 'CHEVROLET');
INSERT INTO `marca` VALUES ('03', 'TOYOTA');

-- ----------------------------
-- Table structure for modelo
-- ----------------------------
DROP TABLE IF EXISTS `modelo`;
CREATE TABLE `modelo` (
  `MOD_CODIGO` char(2) NOT NULL,
  `MAR_CODIGO` char(2) DEFAULT NULL,
  `MOD_NOMBRE` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`MOD_CODIGO`),
  KEY `FK_MARCA_MODELO` (`MAR_CODIGO`),
  CONSTRAINT `FK_MARCA_MODELO` FOREIGN KEY (`MAR_CODIGO`) REFERENCES `marca` (`MAR_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modelo
-- ----------------------------
INSERT INTO `modelo` VALUES ('01', '01', 'ECOSPORT');
INSERT INTO `modelo` VALUES ('02', '01', 'F150');
INSERT INTO `modelo` VALUES ('03', '02', 'PRIUS');
INSERT INTO `modelo` VALUES ('04', '02', 'RAV');

-- ----------------------------
-- Table structure for usuarios
-- ----------------------------
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `USU_CEDULA` char(10) NOT NULL,
  `USU_NOMBRE` varchar(30) DEFAULT NULL,
  `USU_APELLIDO` varchar(30) DEFAULT NULL,
  `USU_PERFIL` varchar(30) DEFAULT NULL,
  `USU_CLAVE` char(4) DEFAULT NULL,
  `USU_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USU_CEDULA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usuarios
-- ----------------------------

-- ----------------------------
-- Table structure for viajes
-- ----------------------------
DROP TABLE IF EXISTS `viajes`;
CREATE TABLE `viajes` (
  `VIA_CODIGO` int(8) NOT NULL AUTO_INCREMENT,
  `CIU_CODIGO` char(2) DEFAULT NULL,
  `AUT_PLACA` char(7) DEFAULT NULL,
  `CIU_CIU_CODIGO` char(2) DEFAULT NULL,
  `VIA_FECHASALIDA` date DEFAULT NULL,
  `VIA_FECHALLEGADA` date DEFAULT NULL,
  `VIA_COSTO` decimal(4,2) DEFAULT NULL,
  `VIA_KM` int(11) DEFAULT NULL,
  `VIA_PASAJEROSBORDO` int(11) DEFAULT NULL,
  `VIA_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`VIA_CODIGO`),
  KEY `FK_AUTOS_VIAJES` (`AUT_PLACA`),
  KEY `FK_CIUDADES_VIAJESDESTINO` (`CIU_CIU_CODIGO`),
  KEY `FK_CIUDADES_VIAJESORIGEN` (`CIU_CODIGO`),
  CONSTRAINT `FK_AUTOS_VIAJES` FOREIGN KEY (`AUT_PLACA`) REFERENCES `autos` (`AUT_PLACA`),
  CONSTRAINT `FK_CIUDADES_VIAJESDESTINO` FOREIGN KEY (`CIU_CIU_CODIGO`) REFERENCES `ciudades` (`CIU_CODIGO`),
  CONSTRAINT `FK_CIUDADES_VIAJESORIGEN` FOREIGN KEY (`CIU_CODIGO`) REFERENCES `ciudades` (`CIU_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of viajes
-- ----------------------------
