<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resultado do Quiz</title>
   
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .correct {
            color: green;
            font-weight: bold;
        }
        .incorrect {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body class="container">
    <div class="mt-5">
        <h1 class="text-center">Resultado do Quiz</h1>
        <p class="text-center">Nome: ${name}</p>
        <p class="text-center">Voce acertou <span class="font-weight-bold">${score}</span> de <span class="font-weight-bold">${total}</span> perguntas!</p>

        <c:forEach var="userAnswer" items="${userAnswers}">
            <div class="card mt-3">
                <div class="card-body">
                    <p><strong>${userAnswer.question.text}</strong></p>
                    <c:forEach var="option" items="${userAnswer.question.options}">
                        <div>
                            <input type="radio" disabled
                                   <c:if test="${option == userAnswer.userAnswer}">checked</c:if>>
                            <label class="
                                   <c:if test='${option == userAnswer.question.correctAnswer}'> correct</c:if>
                                   <c:if test='${option == userAnswer.userAnswer && option != userAnswer.question.correctAnswer}'> incorrect</c:if>">
                                ${option}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

        <div class="text-center mt-5">
            <a href="/" class="btn btn-primary">Reiniciar Quiz</a>
        </div>
    </div>

  
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>