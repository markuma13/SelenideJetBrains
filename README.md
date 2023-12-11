# SelenideJetBrains
### Проект SelenideJetBrains использует Selenide для автоматизации тестирования функционала страницы [/datagrip](https://www.jetbrains.com/datagrip/) на веб-сайте JetBrains. Вот краткое описание того, как реализованы некоторые методы и какие фреймворки используются:
## [DataGripTest](https://github.com/markuma13/SelenideJetBrains/blob/master/src/test/java/test/DataGripTest.java) класс тестов
Используется JUnit 5 для расширения функциональности тестов и для задания отображаемого имени класса тестов
## [AllureAttachmentsManager](https://github.com/markuma13/SelenideJetBrains/blob/master/src/test/java/com/example/selenidejetbrains/AllureAttachmentsManager.java)
screenshot(): Метод, использующий Selenide для создания скриншота и прикрепления его к отчету Allure.
## [AllureLogger](https://github.com/markuma13/SelenideJetBrains/blob/master/src/test/java/com/example/selenidejetbrains/AllureLogger.java)
Два метода для логирования информации. Первый просто логирует текст, второй, кроме текста, также добавляет скриншот с использованием AllureAttachmentsManager
## AssertionHelper
Класс хелпер для упрощения и улучшения читаемости проверок с использованием Assert-методов.
## MyExtension 
Расширение, которое при наличии исключения после выполнения теста добавляет скриншот в отчет Allure

## Так же остальные [страницы](https://github.com/markuma13/SelenideJetBrains/tree/master/src/test/java/pages) для работы тестов с использованием Selenide

