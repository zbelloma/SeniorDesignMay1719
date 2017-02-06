package senior_Design_Testing;

public class Main_Testing {
	public static void main(String args[]){
	//WORD 0xFFFF – start of spectrum
	//WORD Data size flag (0Data is WORD’s, 1 Data is DWORD’s)
	//WORD Number of Scans Accumulated
	//WORD Integration time in milliseconds
	//WORD FPGA Established Baseline value (MSW)
	//WORD FPGA Established Baseline value (LSW)
	//WORD pixel mode
	//WORDs if pixel mode not 0, indicates parameters passed to the Pixel Mode command (P)
	//(D)WORDs spectra data depending on Data size flag
	//WORD 0xFFFD – end of spectrum

	String output = "S 65535 0 1 6 1485 0 0 1444 1489 1485 1482 1483 1496 1451 1483 1487 1500 1484 1444 1490 1484 1450 1448 1448 1461 1460 1459 1464 1480 1481 1514 1468 1490 1442 1449 1453 1500 1507 1439 1475 1483 1490 1492 1458 1486 1476 1479 1464 1475 1449 1468 1534 1518 1480 1491 1488 1473 1511 1502 1446 1482 1515 1472 1481 1455 1473 1484 1508 1497 1486 1499 1498 1492 1485 1475 1472 1465 1466 1477 1504 1498 1463 1477 1497 1462 1459 1456 1489 1485 1447 1468 1474 1479 1495 1438 1474 1469 1476 1472 1497 1473 1489 1456 1478 1473 1443 1481 1474 1505 1475 1466 1487 1465 1471 1472 1507 1512 1453 1510 1474 1430 1492 1473 1437 1481 1475 1500 1455 1507 1487 1490 1478 1456 1450 1470 1477 1467 1474 1475 1469 1465 1482 1511 1466 1503 1475 1461 1486 1493 1486 1464 1483 1473 1437 1499 1464 1444 1445 1477 1452 1442 1502 1499 1461 1458 1507 1477 1464 1487 1469 1478 1475 1446 1526 1447 1477 1496 1481 1457 1486 1477 1476 1463 1471 1499 1499 1500 1464 1468 1482 1504 1480 1470 1500 1467 1500 1468 1447 1514 1450 1488 1458 1480 1485 1477 1450 1507 1480 1507 1495 1451 1494 1504 1452 1452 1452 1487 1486 1484 1476 1534 1486 1475 1484 1518 1457 1467 1478 1470 1481 1501 1476 1439 1485 1487 1463 1466 1440 1471 1482 1464 1475 1476 1482 1479 1470 1516 1478 1431 1440 1471 1478 1439 1490 1486 1496 1471 1468 1503 1499 1516 1480 1516 1464 1486 1482 1480 1486 1486 1417 1489 1492 1449 1465 1463 1481 1504 1491 1465 1462 1487 1452 1456 1443 1463 1476 1458 1462 1512 1476 1452 1453 1439 1478 1484 1452 1495 1484 1473 1476 1468 1498 1520 1505 1474 1476 1471 1468 1497 1501 1492 1494 1466 1449 1499 1414 1485 1487 1457 1520 1513 1471 1496 1495 1476 1493 1516 1436 1506 1490 1429 1526 1464 1443 1515 1509 1501 1500 1508 1492 1507 1453 1478 1502 1488 1474 1475 1505 1463 1480 1499 1494 1453 1456 1498 1470 1449 1457 1466 1487 1481 1501 1459 1465 1510 1469 1509 1448 1469 1457 1477 1499 1466 1481 1473 1475 1476 1463 1481 1463 1516 1462 1464 1463 1480 1440 1487 1458 1452 1498 1444 1480 1517 1490 1467 1476 1458 1477 1493 1490 1510 1495 1491 1515 1463 1455 1486 1484 1512 1486 1448 1477 1470 1504 1491 1482 1459 1485 1492 1462 1473 1484 1471 1486 1477 1489 1484 1460 1477 1475 1483 1500 1444 1481 1492 1504 1482 1476 1475 1466 1462 1481 1468 1488 1481 1491 1489 1482 1490 1481 1472 1451 1519 1457 1507 1494 1496 1448 1463 1480 1479 1500 1458 1450 1486 1498 1488 1471 1497 1475 1465 1480 1514 1445 1470 1465 1480 1509 1477 1499 1473 1508 1491 1471 1450 1449 1494 1512 1454 1457 1495 1451 1518 1465 1469 1480 1482 1499 1489 1499 1456 1467 1476 1469 1496 1490 1502 1502 1477 1454 1503 1471 1527 1449 1471 1478 1494 1458 1463 1518 1465 1489 1471 1458 1471 1465 1479 1458 1459 1494 1475 1492 1485 1454 1457 1490 1480 1497 1487 1473 1477 1476 1475 1494 1469 1499 1502 1466 1529 1477 1486 1461 1473 1447 1455 1485 1449 1479 1454 1504 1505 1465 1496 1487 1478 1467 1465 1480 1464 1463 1494 1503 1456 1500 1500 1497 1514 1520 1466 1488 1483 1469 1462 1495 1480 1474 1471 1467 1486 1478 1459 1446 1502 1455 1491 1470 1471 1469 1466 1464 1487 1478 1467 1443 1474 1500 1481 1472 1552 1495 1476 1477 1512 1448 1472 1456 1469 1452 1476 1474 1456 1483 1442 1480 1471 1492 1496 1463 1522 1450 1452 1486 1488 1473 1463 1436 1481 1460 1529 1468 1484 1507 1460 1507 1504 1428 1508 1474 1448 1468 1486 1490 1491 1456 1462 1477 1489 1512 1464 1503 1529 1481 1472 1478 1456 1455 1467 1468 1503 1490 1472 1456 1444 1494 1523 1458 1480 1466 1493 1498 1461 1462 1497 1445 1491 1471 1493 1455 1469 1448 1492 1501 1492 1483 1462 1506 1459 1458 1466 1469 1507 1466 1515 1467 1511 1496 1473 1482 1508 1476 1486 1445 1519 1476 1500 1487 1511 1470 1456 1468 1492 1486 1473 1504 1466 1460 1461 1491 1478 1445 1452 1485 1452 1485 1451 1486 1469 1468 1471 1458 1487 1495 1482 1474 1453 1516 1500 1445 1465 1469 1490 1480 1475 1423 1481 1513 1458 1454 1472 1451 1457 1463 1455 1464 1488 1477 1486 1478 1458 1444 1453 1505 1507 1481 1442 1473 1505 1454 1481 1451 1505 1473 1451 1499 1500 1457 1491 1478 1458 1510 1451 1466 1503 1475 1467 1468 1499 1446 1484 1472 1468 1470 1503 1472 1472 1494 1481 1450 1468 1462 1455 1461 1465 1464 1449 1456 1485 1467 1488 1485 1460 1463 1458 1502 1519 1480 1486 1485 1481 1478 1470 1479 1525 1493 1487 1511 1456 1465 1449 1482 1472 1480 1495 1479 1511 1470 1464 1510 1484 1462 1457 1480 1475 1498 1459 1491 1484 1470 1441 1470 1482 1446 1465 1461 1513 1491 1463 1461 1483 1473 1486 1474 1471 1512 1473 1459 1519 1480 1486 1449 1502 1486 1460 1439 1475 1467 1531 1458 1430 1472 1496 1482 1515 1485 1474 1498 1479 1448 1471 1449 1453 1442 1474 1480 1509 1497 1473 1471 1476 1469 1477 1443 1450 1472 1455 1485 1464 1505 1466 1502 1465 1454 1461 1461 1459 1453 1463 1495 1485 1450 1489 1468 1494 1451 1459 1485 1448 1471 1444 1498 1479 1470 1480 1480 1471 1464 1505 1504 1486 1476 1504 1485 1518 1497 1500 1458 1507 1479 1515 1488 1521 1518 1484 1498 1502 1523 1505 1516 1445 1479 1514 1473 1460 1463 1466 1529 1489 1500 1478 1491 1513 1455 1462 1509 1460 1511 1436 1503 1475 1454 1494 1474 1470 1456 1507 1477 1487 1489 1490 1458 1515 1466 1505 1453 1493 1428 1483 1485 1505 1467 1464 1446 1461 1453 1460 1474 1469 1471 1515 1482 1459 1453 1474 1476 1479 1478 1455 1489 1474 1472 1477 1464 1509 1447 1498 1497 1490 1471 1512 1447 1489 1515 1444 1426 1452 1455 1498 1488 1511 1486 1492 1499 1460 1486 1469 1470 1450 1500 1469 1453 1489 1485 1502 1479 1455 1483 1487 1495 1512 1528 1472 1464 1484 1496 1490 1470 1490 1466 1468 1469 1463 1439 1485 1472 1491 1494 1462 1457 1472 1440 1479 1459 1503 1492 1500 1496 1510 1490 1510 1476 1490 1471 1473 1451 1472 1503 1460 1507 1462 1462 1466 1466 1498 1511 1499 1487 1463 1499 1502 1486 1498 1478 1472 1485 1477 1505 1519 1466 1475 1491 1457 1470 1465 1496 1440 1473 1501 1501 1484 1480 1445 1461 1489 1476 1482 1491 1499 1469 1502 1495 1494 1500 1459 1465 1514 1489 1482 1464 1501 1477 1505 1469 1448 1473 1481 1497 1483 1518 1495 1503 1453 1468 1535 1446 1516 1511 1466 1478 1507 1482 1500 1455 1477 1468 1497 1472 1504 1493 1478 1471 1478 1468 1515 1451 1468 1459 1500 1460 1524 1478 1452 1500 1478 1496 1495 1504 1462 1489 1476 1460 1474 1491 1467 1506 1492 1505 1492 1480 1511 1464 1503 1450 1456 1496 1516 1456 1499 1462 1490 1442 1464 1494 1487 1451 1491 1480 1491 1454 1446 1464 1494 1472 1475 1477 1492 1467 1450 1476 1511 1494 1474 1525 1482 1484 1473 1491 1513 1479 1484 1481 1430 1479 1492 1466 1467 1459 1496 1468 1447 1492 1512 1504 1478 1498 1488 1482 1504 1445 1497 1494 1511 1480 1485 1495 1489 1504 1496 1487 1510 1467 1496 1480 1496 1499 1494 1486 1456 1480 1482 1461 1503 1514 1470 1457 1459 1484 1493 1465 1519 1486 1510 1487 1503 1466 1487 1491 1486 1474 1479 1498 1476 1493 1515 1512 1487 1458 1473 1466 1480 1490 1519 1451 1511 1500 1469 1488 1492 1456 1500 1477 1476 1468 1502 1482 1525 1452 1470 1457 1481 1469 1502 1463 1469 1499 1457 1467 1478 1471 1488 1477 1492 1473 1476 1452 1467 1486 1477 1510 1448 1532 1445 1463 1487 1470 1488 1485 1474 1514 1513 1446 1479 1495 1471 1429 1517 1512 1482 1442 1525 1461 1482 1473 1495 1435 1463 1467 1515 1462 1452 1438 1464 1452 1469 1490 1497 1474 1450 1471 1515 1475 1481 1455 1468 1456 1482 1473 1458 1476 1456 1489 1471 1481 1482 1468 1487 1470 1449 1467 1464 1446 1473 1477 1470 1456 1483 1433 1469 1469 1498 1466 1453 1507 1463 1481 1451 1435 1474 1464 1465 1456 1478 1435 1470 1469 1484 1461 1514 1443 1485 1470 1439 1467 1496 1485 1481 1452 1504 1472 1503 1459 1486 1470 1570 1446 1486 1455 1507 1447 1475 1479 1456 1508 1508 1482 1498 1490 1502 1464 1441 1489 1499 1459 1434 1469 1521 1480 1448 1477 1497 1497 1440 1452 1475 1483 1503 1486 1498 1474 1496 1470 1447 1487 1483 1474 1487 1514 1478 1450 1485 1464 1469 1481 1473 1469 1458 1471 1487 1457 1489 1477 1502 1460 1445 1467 1489 1473 1468 1485 1496 1453 1443 1458 1503 1472 1517 1452 1467 1474 1494 1471 1474 1450 1451 1469 1484 1437 1485 1440 1467 1436 1485 1433 1474 1470 1482 1471 1499 1475 1447 1451 1446 1480 1480 1464 1492 1431 1462 1498 1516 1492 1466 1501 1465 1470 1482 1451 1511 1461 1509 1481 1472 1488 1513 1487 1483 1474 1456 1479 1502 1514 1475 1462 1480 1501 1541 1476 1483 1474 1489 1467 1478 1430 1517 1489 1444 1467 1491 1455 1487 1464 1524 1454 1460 1484 1478 1494 1439 1464 1478 1467 1421 1508 1484 1491 1478 1479 1434 1502 1498 1455 1456 1519 1499 1465 1488 1500 1468 1486 1455 1462 1452 1476 1481 1494 1467 1466 1466 1467 1485 1480 1501 1477 1438 1433 1504 1469 1447 1447 1489 1482 1479 1458 1474 1499 1491 1446 1463 1475 1484 1456 1483 1478 1470 1484 1486 1500 1441 1479 1474 1479 1448 1457 1464 1479 1472 1440 1513 1448 1440 1492 1454 1495 1516 1466 1490 1471 1472 1502 1497 1465 1442 1434 1487 1495 1471 1495 1485 1484 1470 1452 1440 1473 1514 1467 1472 1460 1461 1487 1486 1472 1446 1501 1484 1459 1470 1449 1453 1460 1502 1487 1469 1513 1456 1487 1478 1455 1501 1478 1446 1475 1454 1481 1501 1483 1480 1493 1455 1485 1450 1470 1439 1474 1457 1467 1459 1442 1491 1487 1462 1476 1473 1430 1480 1507 1454 1441 1469 1480 1504 1453 1467 1437 1526 1468 1515 1468 1473 1487 1468 1488 1488 1447 1482 1460 1512 1474 1484 1481 1474 1502 1449 1492 1439 1481 1471 1451 1454 1454 1469 1448 1468 1513 1527 1459 1462 1444 1467 1500 1452 1461 1484 1482 1492 1470 1465 1476 1443 1484 1487 1497 1458 1478 1458 1488 1479 1471 1507 1483 1484 1406 1496 1438 1448 1442 1477 1474 1459 1460 1465 1481 1459 1454 1466 1453 1443 1470 1449 1481 1475 1496 1475 1437 1491 1470 1474 1462 1467 1450 1489 1505 1495 1474 1455 1475 1491 1474 1457 1472 1480 1462 1453 1469 1478 1446 1475 1489 1507 1493 1493 1456 1431 1474 1469 1499 1458 1476 1469 1473 1478 1483 1462 1479 1493 1473 1424 1487 1502 1439 1513 1486 1496 1447 1466 1503 1445 1472 1460 1417 1490 1455 1498 1458 1479 1461 1474 1475 1527 1466 1463 1459 1442 1468 1487 1470 1520 1467 1502 1454 1495 1484 1494 1447 1495 1416 1496 1443 1456 1484 1450 1512 1464 1476 1448 1511 1460 1476 1508 1488 1464 1478 1472 1522 1476 1440 1463 1446 1479 1460 1496 1464 1469 1468 1464 1482 1500 1479 1468 1476 1435 1476 1448 1486 1460 1446 1482 1488 1481 1474 1471 1483 1486 1483 1501 1444 1504 1467 1476 1507 1466 1440 1480 1456 1464 1481 1524 1486 1476 1448 1475 1456 1487 1470 1460 1436 1462 1462 1447 1506 1480 1443 1489 1444 1523 1462 1448 1457 1464 1458 1481 1472 1477 1440 1467 1465 1464 1471 1496 1466 1479 1452 1450 1488 1471 1500 1472 1479 1492 1486 1470 1462 1456 1460 1463 1465 1495 1460 1464 1428 1481 1490 1480 1476 1496 1463 1479 1486 1500 1484 1452 1498 1457 1474 1426 1458 1441 1457 1459 1447 1477 1467 1489 1470 1472 1405 1490 1507 1475 1478 1499 1487 1442 1490 1497 1466 1468 1467 1467 1487 1473 1458 1500 1460 1486 1475 1495 1472 1484 1439 1498 1508 1480 1460 1460 1481 1446 1460 1474 1450 1461 1463 1466 1449 1487 1469 1436 1463 1471 1446 1451 1456 1450 1472 1479 1474 1472 1493 1482 1476 1495 1472 1445 1461 1456 1472 1480 1459 1513 1460 1470 1450 1500 1439 1444 1467 1459 1484 1456 1466 1508 1460 1414 1450 1425 1514 1456 1485 1461 1491 1469 1468 1497 1507 1460 1461 1482 1455 1504 1460 1503 1457 1464 1484 1496 1466 1473 1496 1482 1440 1475 1459 1460 1449 1486 1446 1468 1480 1449 1487 1477 1494 1508 1497 1496 1459 1457 1450 1469 1476 1496 1485 1467 1505 1476 1470 1481 1494 1473 1482 1499 1475 1509 1427 1495 1452 1454 1495 1448 1494 1458 1497 1466 1428 1467 1481 1489 1449 1472 1478 1451 1467 1473 1422 1487 1420 1456 1481 1465 1448 1472 1468 1477 1469 1464 1446 1442 1499 1469 1454 1489 1463 1454 1471 1463 1505 1540 1465 1490 1470 1465 1488 1461 1497 1489 1477 1492 1463 1455 1499 1443 1488 1454 1458 1512 1450 1456 1487 1479 1490 1455 1458 1496 1506 1507 1480 1473 1464 1481 1454 1482 1503 1480 1451 1489 1445 1492 1448 1402 1487 1505 1458 1452 1433 1482 1441 1489 1496 1471 1475 1469 1483 1466 1478 1474 1481 1490 1457 1503 1461 1475 1447 1461 1492 1431 1468 1448 1437 1436 1502 1538 1450 1469 1448 1493 1467 1473 1507 1448 1483 1474 1471 1458 1436 1496 1463 1484 1505 1476 1446 1500 1494 1491 1494 1495 1458 1459 1454 1477 1510 1447 1518 1497 1507 1477 1430 1467 1449 1520 1481 1523 1446 1441 1486 1465 1426 1449 1469 1453 1465 1519 1521 1469 1475 1463 1472 1495 1467 1468 1436 1490 1478 1502 1494 1508 1437 1503 1487 1475 1452 1435 1460 1494 1496 1468 1476 1426 1445 1480 1464 1456 1467 1481 1470 1498 1476 1454 1463 1525 1507 1446 1431 1444 1483 1446 1475 1496 1438 1454 1453 1488 1494 1429 1479 1469 1454 1469 1457 1504 1489 1481 1457 1433 1466 1471 1449 1457 1467 1467 1424 1493 1461 1482 1468 1488 1447 1483 1463 1462 1492 1494 1472 1480 1497 1496 1479 1474 1508 1491 1464 1474 1493 1474 1486 1470 1433 1489 1441 1483 1443 1469 1427 1485 1430 1480 1443 1468 1483 1491 1456 1464 1474 1458 1450 1462 1452 1491 1527 1491 1447 1474 1482 1472 1480 1486 1439 1455 1446 1526 1479 1500 1500 1475 1461 1466 1478 1492 1450 1461 1461 1471 1448 1545 1457 1463 1522 1456 1455 1459 1484 1501 1456 1478 1450 1476 1485 1474 1497 1481 1440 1475 1465 1497 1489 1428 1478 1447 1499 1477 1468 1469 1480 1502 1503 1498 1471 1484 1475 1468 1453 1460 1462 1498 1424 1505 1450 1505 1491 1430 1479 1459 1451 1466 1445 1458 1498 1494 1461 1475 1441 1471 1485 1479 1494 1471 1460 1479 1484 1453 1466 1476 1470 1468 1477 1473 1482 1409 1448 1431 1463 1473 1471 1470 1450 1466 1469 1462 1431 1461 1475 1505 1493 1508 1471 1490 1489 1459 1490 1488 1513 1493 1466 1453 1508 1485 1455 1477 1455 1464 1481 1472 1453 1498 1444 1463 1458 1479 1489 1492 1428 1433 1438 1475 1450 1472 1461 1439 1502 1481 1470 1491 1464 1463 1517 1499 1436 1498 1496 1475 1502 1466 1447 1440 1458 1483 1435 1450 1466 1441 1426 1460 1461 1445 1469 1505 1482 1483 1479 1463 1503 1451 1488 1450 1460 1478 1472 1488 1471 1508 1481 1466 1468 1434 1500 1471 1481 1508 1431 1446 1466 1465 1508 1472 1451 1477 1479 1482 1462 1480 1469 1484 1465 1461 1460 1479 1502 1467 1479 1492 1452 1471 1462 1494 1478 1478 1442 1473 1483 1473 1443 1423 1495 1434 1477 1508 1504 1490 1499 1467 1500 1466 1531 1456 1466 1454 1477 1472 1472 1487 1489 1543 1470 1488 1462 1513 1446 1487 1470 1475 1419 1443 1454 1485 1480 1468 1472 1461 1469 1486 1494 1454 1497 1483 1477 1473 1441 1499 1474 1473 1463 1481 1448 1489 1473 1442 1479 1462 1457 1485 1469 1474 1475 1483 1479 1497 1491 1492 1486 1478 1471 1452 1452 1495 1493 1470 1476 1474 1470 1455 1479 1506 1471 1467 1466 1498 1469 1490 1444 1459 1467 1437 1443 1462 1498 1440 1421 1470 1475 1475 1476 1483 1457 1488 1484 1512 1508 1469 1478 1512 1486 1502 1481 1505 1472 1481 1531 1475 1491 1472 1479 1478 1470 1456 1498 1440 1431 1493 1480 1481 1482 1420 1503 1476 1525 1463 1470 1500 1442 1471 1510 1495 1456 1505 1458 1479 1468 1454 1483 1455 1464 1466 1459 1484 1466 1446 1441 1476 1450 1461 1477 1481 1442 1449 1491 1479 1429 1495 1437 1492 1468 1487 1486 1437 1460 1472 1468 1437 1501 1474 1496 1488 1481 1463 1483 1492 1474 1469 1476 1480 1478 1465 1471 1494 1466 1485 1471 1466 1473 1509 1461 1490 1481 1473 1471 1463 1499 1493 1449 1474 1476 1450 1443 1497 1507 1536 1487 1446 1441 1480 1460 1486 1507 1459 1480 1432 1437 1462 1474 1494 1442 1461 1409 1479 1509 1493 1465 1463 1454 1429 1449 1519 1466 1503 1479 1471 1406 1447 1454 1422 1434 1463 1452 1459 1467 1477 1480 1488 1474 1514 1503 1464 1486 1501 1479 1446 1452 1469 1465 1472 1489 1460 1505 1481 1462 1485 1492 1437 1487 1447 1479 1486 1471 1510 1460 1462 1447 1456 1489 1446 1451 1460 1488 1469 1461 1460 1482 1476 1473 1479 1470 1483 1478 1457 1472 1497 1462 1473 1469 1532 1475 1465 1471 1457 1454 1486 1485 1492 1483 1481 1473 1469 1466 1482 1501 1463 1467 1455 1479 1441 1512 1454 1434 1464 1487 1454 1435 1469 1460 1498 1473 1480 1461 1446 1431 1451 1492 1449 1429 1494 1456 1434 1487 1417 1455 1460 1481 1464 1471 1506 1486 1465 1468 1496 1483 1499 1471 1442 1482 1466 1470 1410 1425 1475 1473 1473 1445 1471 1476 1463 1491 1463 1486 1478 1482 1474 1418 1463 1479 1429 1461 1493 1488 1488 1463 1473 1475 1468 1458 1482 1477 1462 1500 1475 1481 1469 1473 1488 1472 1489 1501 1479 1454 1458 1469 1489 1470 1465 1507 1471 1471 1465 1477 1452 1454 1450 1459 1433 1470 1485 1441 1456 1485 1459 1455 1443 1474 1487 1460 1484 1474 1473 1470 1502 1506 1471 1483 1491 1495 1479 1508 1470 1496 1454 1476 1434 1515 1482 1461 1452 1471 1450 1461 1453 1458 1472 1451 1469 1485 1477 1452 1476 1451 1497 1487 1470 1454 1478 1453 1478 1487 1463 1477 1447 1469 1466 1444 1477 1478 1495 1477 1434 1449 1477 1498 1469 1447 1473 1459 1493 1439 1488 1465 1494 1511 1432 1470 1467 1479 1484 1460 1507 1488 1496 1482 1472 1464 1464 1458 1491 1474 1446 1474 1499 1468 1493 1455 1441 1472 1472 1483 1458 1503 1504 1456 1468 1461 1476 1464 1450 1490 1459 1466 1484 1469 1465 1490 1471 1472 1465 1449 1503 1454 1442 1471 1459 1478 1447 1465 1456 1496 1471 1450 1435 1464 1450 1450 1467 1474 1454 1449 1475 1466 1479 1439 1475 1448 1493 1496 1480 1466 1498 1487 1462 1482 1499 1471 1488 1497 1483 1480 1468 1470 1477 1484 1466 1480 1449 1467 1458 1471 1484 1483 1438 1439 1452 1480 1492 1469 1434 1454 1448 1468 1441 1441 1505 1488 1432 1461 1497 1469 1466 1470 1467 1433 1485 1491 1455 1454 1503 1481 1474 1498 1457 1494 1491 1489 1489 1457 1469 1428 1449 1501 1519 1439 1438 1511 1479 1464 1462 1467 1496 1462 1492 1457 1460 1448 1429 1472 1511 1478 1480 1473 1508 1476 1447 1474 1483 1416 1464 1498 1469 1472 1492 1488 1491 1485 1458 1456 1440 1460 1459 1515 1480 1458 1438 1484 1418 1474 1479 1491 1518 1481 1433 1464 1454 1441 1475 1461 1470 1479 1465 1462 1451 1475 1468 1469 1497 1440 1464 1476 1487 1489 1472 1474 1466 1486 1451 1464 1479 1467 1487 1468 1452 1472 1464 1450 1458 1470 1454 1480 1493 1449 1439 1453 1456 1493 1463 1470 1461 1486 1473 1468 1471 1455 1471 1443 1468 1450 1462 1458 1448 1450 1471 1443 1471 1468 1421 1462 1490 1494 1492 1461 1507 1477 1473 1426 1471 1440 1495 1457 1465 1482 1470 1452 1493 1475 1509 1483 1466 1461 1478 1463 1466 1427 1493 1473 1516 1445 1458 1421 1469 1442 1473 1461 1491 1477 1471 1449 1505 1458 1449 1496 1453 1461 1463 1461 1494 1435 1463 1508 1491 1449 1516 1496 1452 1496 1489 1448 1470 1426 1476 1453 1508 1433 1467 1456 1471 1460 1479 1432 1482 1442 1448 1450 1504 1471 1473 1472 1501 1444 1490 1467 1496 1451 1498 1457 1482 1500 1502 1455 1451 1479 1486 1465 1436 1444 1474 1491 1475 1472 1442 1465 1446 1474 1452 1461 1433 1437 1433 1462 1459 1457 1475 1473 1437 1474 1471 1478 1511 1463 1487 1478 1473 1474 1498 1436 1481 1487 1499 1493 1492 1479 1462 1492 1489 1482 1508 1433 1502 1475 1449 1452 1513 1437 1478 1469 1516 1489 1475 1489 1479 1460 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 1499 65533";

    String pixels[] = output_to_pixels(output);
	//pixels[] now stores all of the pixel data obtained from the spectrometer
	//System.out.println("Number of scans: " + scans);
	//System.out.println("Integration time: " + integration_Time);
	for(int j = 0; j < pixels.length -1; j++){
		System.out.println("Pixel " + j + ": " + pixels[j]);
	}
  }
	public static String[] output_to_pixels(String input){
		//input = input.replace("\n", "");
		String[] output_Data = input.split(" ");

		String data_Mode = output_Data[2]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
		//String scans = output_Data[3]; //Number of scans taken
		//String integration_Time = output_Data[4]; //Time taken to obtain sample data
		String pixels[] = new String[(output_Data.length-9)/2];
		Integer pixel_Index = 0;

		if(data_Mode.equals("0")){
			for(int i = 8; i < output_Data.length-2; i += 2){
				pixels[pixel_Index] = output_Data[i+1] + output_Data[i];
				pixel_Index++;
			}
		} else {
			for(int i = 8; i < output_Data.length; i+=4){
				pixels[pixel_Index] = output_Data[i+3] + output_Data[i+2] + output_Data[i+1] + output_Data[i];
				pixel_Index++;
			}
		}

		return pixels;
	}
}