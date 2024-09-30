package com.quiznovo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {

    private Map<String, List<Question>> questionsByDifficulty;
    private String selectedDifficulty;
    private String userName;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private List<UserAnswer> userAnswers;

    public QuizController() {
        // Inicializa o mapa de perguntas por nível de dificuldade
        questionsByDifficulty = new HashMap<>();
        userAnswers = new ArrayList<>();

        // Perguntas de nível fácil
        List<Question> easyQuestions = new ArrayList<>();
        easyQuestions.add(new Question("Qual é a capital da França?", 
            List.of("Paris", "Londres", "Roma", "Berlim", "Madri"), "Paris"));
        easyQuestions.add(new Question("Qual é o resultado de 2 + 2?", 
            List.of("3", "4", "5", "6", "7"), "4"));
        easyQuestions.add(new Question("Qual é a cor do céu em um dia claro?", 
            List.of("Azul", "Verde", "Vermelho", "Amarelo", "Cinza"), "Azul"));
        easyQuestions.add(new Question("Quantos dias há em uma semana?", 
            List.of("5", "6", "7", "8", "9"), "7"));
        easyQuestions.add(new Question("Qual é o maior mamífero?", 
            List.of("Elefante", "Baleia Azul", "Rinoceronte", "Girafa", "Leão"), "Baleia Azul"));

        // Perguntas de nível médio
        List<Question> mediumQuestions = new ArrayList<>();
        mediumQuestions.add(new Question("Qual o maior planeta do nosso sistema solar?", 
            List.of("Terra", "Marte", "Jupiter", "Saturno", "Venus"), "Jupiter"));
        mediumQuestions.add(new Question("Quem pintou a Mona Lisa?", 
            List.of("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo", "Raphael"), "Leonardo da Vinci"));
        mediumQuestions.add(new Question("Em que ano o homem pisou na Lua pela primeira vez?", 
            List.of("1959", "1969", "1979", "1989", "1999"), "1969"));
        mediumQuestions.add(new Question("Qual é a fórmula química da água?", 
            List.of("H2O", "CO2", "NaCl", "O2", "CH4"), "H2O"));
        mediumQuestions.add(new Question("Qual o país com maior população do mundo?", 
            List.of("Índia", "Estados Unidos", "Brasil", "China", "Russia"), "China"));

        // Perguntas de nível difícil
        List<Question> hardQuestions = new ArrayList<>();
        hardQuestions.add(new Question("Quem escreveu 'Dom Quixote'?", 
            List.of("Gabriel Garcia Marquez", "Miguel de Cervantes", "J.K. Rowling", "Ernest Hemingway", "William Shakespeare"), "Miguel de Cervantes"));
        hardQuestions.add(new Question("Qual é a constante de Planck?", 
            List.of("6.626 x 10^-34 Js", "3.00 x 10^8 m/s", "9.81 m/s^2", "1.62 x 10^-19 C", "8.99 x 10^9 Nm^2/C^2"), "6.626 x 10^-34 Js"));
        hardQuestions.add(new Question("Qual é a capital do Cazaquistão?", 
            List.of("Nur-Sultan", "Almaty", "Bishkek", "Tashkent", "Astana"), "Nur-Sultan"));
        hardQuestions.add(new Question("Qual foi o primeiro elemento químico a ser descoberto?", 
            List.of("Oxigênio", "Hidrogenio", "Fosforo", "Cobre", "Enxofre"), "Fosforo"));
        hardQuestions.add(new Question("Quem é o autor de 'A República'?", 
            List.of("Platao", "Aristóteles", "Sócrates", "Descartes", "Nietzsche"), "Platao"));

        // Adiciona perguntas ao mapa
        questionsByDifficulty.put("easy", easyQuestions);
        questionsByDifficulty.put("medium", mediumQuestions);
        questionsByDifficulty.put("hard", hardQuestions);
    }

    @GetMapping("/")
    public String showStartPage() {
        return "start"; // Página inicial para selecionar o nível de dificuldade
    }

    @PostMapping("/start")
    public String startQuiz(@RequestParam("difficulty") String difficulty, @RequestParam("name") String name) {
        this.selectedDifficulty = difficulty;
        this.userName = name;
        this.currentQuestionIndex = 0; // Reseta o índice de pergunta
        this.score = 0; // Reseta a pontuação
        this.userAnswers.clear(); // Limpa as respostas anteriores
        return "redirect:/quiz";
    }

    @GetMapping("/quiz")
    public String showQuiz(Model model) {
        List<Question> questions = questionsByDifficulty.get(selectedDifficulty);

        if (currentQuestionIndex < questions.size()) {
            model.addAttribute("question", questions.get(currentQuestionIndex));
            return "quiz"; // Retorna a view `quiz.jsp`
        } else {
            return "redirect:/result"; // Redireciona para o resultado se não houver mais perguntas
        }
    }

    @PostMapping("/quiz")
    public String submitAnswer(@RequestParam("answer") String answer, Model model) {
        List<Question> questions = questionsByDifficulty.get(selectedDifficulty);
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Verifica a resposta e atualiza a pontuação
        if (currentQuestion.getCorrectAnswer().equals(answer)) {
            score++;
        }

        // Adiciona a resposta do usuário para feedback posterior
        userAnswers.add(new UserAnswer(currentQuestion, answer));

        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            model.addAttribute("question", questions.get(currentQuestionIndex));
            return "quiz"; // Retorna a view `quiz.jsp`
        } else {
            return "redirect:/result"; // Redireciona para o resultado se não houver mais perguntas
        }
    }

    @GetMapping("/result")
    public String showResult(Model model) {
        model.addAttribute("name", userName);
        model.addAttribute("score", score);
        model.addAttribute("total", questionsByDifficulty.get(selectedDifficulty).size());
        model.addAttribute("userAnswers", userAnswers);
        return "result"; // Retorna a view `result.jsp`
    }

    public static class Question {
        private String text;
        private List<String> options;
        private String correctAnswer;

        public Question(String text, List<String> options, String correctAnswer) {
            this.text = text;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getText() {
            return text;
        }

        public List<String> getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    public static class UserAnswer {
        private Question question;
        private String userAnswer;

        public UserAnswer(Question question, String userAnswer) {
            this.question = question;
            this.userAnswer = userAnswer;
        }

        public Question getQuestion() {
            return question;
        }

        public String getUserAnswer() {
            return userAnswer;
        }
    }
}
