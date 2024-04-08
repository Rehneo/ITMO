<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Point" %>
<%@ page import="model.PointContainer" %>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Лабораторная работа №2</title>
</head>
<body>
<table id="mainTable">
    <tbody>
    <tr>
        <td id="header" colspan="2">
            <h1 id="labName">Лабораторная работа №2</h1>
            <h3>Выполнил: Мухсинов Сардорбек Пулатович</h3>
            <h3>Группа: P3217</h3>
            <h3>Вариант: 1711</h3>
        </td>
    </tr>
    <tr id="row">
        <td>
            <svg id="svg" width="500px" height="500px">
                <rect x="250" y="50" width="100" height="200" fill="#DF0D21"></rect>
                <polygon points="50,250 250,250 250,150" fill="#DF0D21"></polygon>
                <path fill="#DF0D21" d="M150 250 L250 250 L250 350 A100 100 0 0 1 150 250 Z"></path>
                <line x1="0" x2="500" y1="250" y2="250" stroke="snow" stroke-width="3px"></line>
                <line x1="250" x2="250" y1="0" y2="500" stroke="snow" stroke-width="3px"></line>
                <line x1="50" x2="50" y1="240" y2="260" stroke="snow" stroke-width="3px"></line>
                <line x1="150" x2="150" y1="240" y2="260" stroke="snow" stroke-width="3px"></line>
                <line x1="350" x2="350" y1="240" y2="260" stroke="snow" stroke-width="3px"></line>
                <line x1="450" x2="450" y1="240" y2="260" stroke="snow" stroke-width="3px"></line>
                <line x1="240" x2="260" y1="50" y2="50" stroke="snow" stroke-width="3px"></line>
                <line x1="240" x2="260" y1="150" y2="150" stroke="snow" stroke-width="3px"></line>
                <line x1="240" x2="260" y1="350" y2="350" stroke="snow" stroke-width="3px"></line>
                <line x1="240" x2="260" y1="450" y2="450" stroke="snow" stroke-width="3px"></line>
                <text x="265" y="155" font-size="20px">R/2</text>
                <text x="145" y="230" font-size="20px">-R/2</text>
                <text x="345" y="230" font-size="20px">R/2</text>
                <text x="265" y="355" font-size="20px">-R/2</text>
                <text x="445" y="230" font-size="20px">R</text>
                <text x="265" y="455" font-size="20px">-R</text>
                <text x="265" y="55" font-size="20px">R</text>
                <text x="45" y="230" font-size="20px">-R</text>
            </svg>
        </td>
        <td id="selectTableContainer">
            <table id="selectTable">
                <tr>
                    <td>
                        <label for="selectXTable" class="XYRLabels">X: </label>
                        <table id="selectXTable">
                            <tr id="xLabels">
                                <td><label for="x_3">-3</label></td>
                                <td><label for="x_2">-2</label></td>
                                <td><label for="x_1">-1</label></td>
                                <td><label for="x0">0</label></td>
                                <td><label for="x1">1</label></td>
                                <td><label for="x2">2</label></td>
                                <td><label for="x3">3</label></td>
                                <td><label for="x4">4</label></td>
                                <td><label for="x5">5</label></td>
                            </tr>
                            <tr>
                                <td><input type="radio" name="X" id="x_3" value="-3" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x_2" value="-2" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x_1" value="-1" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x0" value="0" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x1" value="1" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x2" value="2" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x3" value="3" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x4" value="4" onclick="setX(this.value)"></td>
                                <td><input type="radio" name="X" id="x5" value="5" onclick="setX(this.value)"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="XYRLabels" for="Y">Y:</label>
                        <input maxlength="6" id="Y" type="text">
                        <button type="button" id="submitY">Enter</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="XYRLabels" for="R">R:</label>
                        <input maxlength="6" id="R" type="text">
                        <button type="button" id="submitR">Enter</button>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <button type="submit" id="submit">Submit</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table id="showXYRTable" class="showDataTable">
                            <tr>
                                <td><label id = "selectedX">X:  </label></td>
                                <td><label id = "selectedY">Y:  </label></td>
                                <td><label id = "selectedR">R:  </label></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    </tbody>
    <tfoot>
        <tr>
            <td>
                <table id="resultTable" class="showDataTable">
                    <thead>
                    <tr>
                        <td>X</td>
                        <td>Y</td>
                        <td>R</td>
                        <td>Result</td>
                    </tr>
                    </thead>
                    <tbody id="responseData">
                    <%if(application.getAttribute(request.getSession().getId()) != null) {
                        PointContainer container = (PointContainer) application.getAttribute(request.getSession().getId());
                        for(Point p: container){%>
                    <tr>
                        <td><%=p.getX()%></td>
                        <td><%=p.getY()%></td>
                        <td><%=p.getR()%></td>
                        <td><%=p.isInArea()%></td>
                    </tr><%}
                    }%>
                    </tbody>
                </table>
            </td>
        </tr>
    </tfoot>
</table>
<script src="script.js"></script>
<script src="request.js"></script>
</body>
</html>
