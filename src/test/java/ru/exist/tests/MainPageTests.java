package ru.exist.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MainPageTests extends BaseTest {

    @Test
    @DisplayName("Поиск по модели \"Skoda Kodiaq\"")
    public void skodaKodiaqSearchTest() {
        step("Открываем главную страницу", () -> open("/"));

        step("Поиск по названию марки \"Skoda\"", () ->
                $("#carUnChecked").$(byLinkText("Skoda")).click());

        step("Проверяем, что в списке есть модель \"Kodiaq\"", () -> {
            $("#models").shouldHave(text("Kodiaq"));
        });
    }

    @Test
    @DisplayName("Поиск по артикулу запчасти")
    public void searchByPartNumberTest() {
        step("Открываем главную страницу", () -> open("/"));

        String partNo = "212965-6";
        step("Поиск запчасти по артикулу", () -> {
            $("#pcode").setValue(partNo).pressEnter();
        });

        step("Проверяем, что в списке есть необходимая запчасть", () -> {
            $("#priceBody").$(".partno").shouldHave(text(partNo));
        });
    }

    @Test
    @DisplayName("Проверка пунктов главного меню")
    public void catalogItemsTest() {
        step("Открываем главную страницу", () -> open("/"));

        step("Открываем пункт меню \"Каталоги\"", () ->
                $(".mainmenu").$(byTitle("Запчасти и аксессуары в каталогах")).click());

        step("Проверяем, что в каталогах есть все пункты", () -> {
            $$("#menutop a").should(containExactTextsCaseSensitive(
                    List.of("Все каталоги", "Масла и автохимия", "Шины, диски", "Автоэлектроника", "Остальное")));
        });
    }

    @Test
    @DisplayName("Проверка выпадающего меню \"Гараж\"")
    public void garageTest() {
        step("Открываем главную страницу", () -> open("/"));

        step("Открываем выпадающее меню \"Гараж\"", () ->
                $("#garageFloat").click());

        step("Проверяем, что необходимо выбрать ТС", () -> {
            $("#phCarInfo").shouldHave(text("Выберите транспортное средство"));
        });
    }

    @Test
    @DisplayName("Проверка пункта меню \"Автоточки\"")
    public void autoPointsTest() {
        step("Открываем главную страницу", () -> open("/"));

        step("Открываем пункт меню \"Автоточки\"", () ->
                $(".mainmenu").$(byTitle("Автоточки")).click());

        step("Проверяем, что в списке есть все разделы", () -> {
            $$(".page-blocks a").should(containExactTextsCaseSensitive(
                    List.of("Ремонт", "АЗС", "Автомойка", "Автосалон", "Шиномонтаж", "Техосмотр")));
        });
    }
}
