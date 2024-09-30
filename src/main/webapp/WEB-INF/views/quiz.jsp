<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz</title>
 
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        let timer = 60; // Tempo de 1 minuto
        let interval = setInterval(function() {
            timer--;
            document.getElementById('timer').innerText = 'Tempo restante: ' + timer + ' segundos';
            if (timer <= 0) {
                clearInterval(interval);
                document.getElementById('quizForm').submit(); // Submete o formulário automaticamente quando o tempo acabar
            }
        }, 1000);
    </script>
</head>
<body>
    <div class="container">
        <h1>Quiz</h1>
        <p id="timer" style="font-weight: bold; color: red;">Tempo restante: 60 segundos</p>
        <form id="quizForm" action="/quiz" method="post">
            <div class="form-group">
                <p>${question.text}</p>
                <c:forEach var="option" items="${question.options}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="answer" value="${option}" id="${option}">
                        <label class="form-check-label" for="${option}">
                            ${option}
                        </label>
                    </div>
                </c:forEach>
            </div>
            <button type="submit" class="btn btn-primary">Enviar Resposta</button>
        </form>
    </div>
</body>
</html>
