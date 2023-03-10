package ru.my.dreamjob.repository;


import ru.my.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841 #281377]
 * MemoryVacancyRepository хранилище данных вакансий в памяти.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
public class MemoryVacancyRepository implements VacancyRepository {
    private final AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Description intern JOB Java", true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Description Junior JOB Java Developer", false, 2, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Description Junior+ JOB Java Developer", true, 3, 0));
        save(new Vacancy(0, "Middle Java Developer", "Description Middle JOB Java Developer", false, 1, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Description Middle+ JOB Java Developer", true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer", "Description Senior JOB Java Developer", false, 3, 0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancies.computeIfAbsent(nextId.incrementAndGet(),
                key -> new Vacancy(key, vacancy.getTitle(), vacancy.getDescription(),
                        vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId()));
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(
                vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(
                        oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription(),
                        vacancy.getVisible(),
                        vacancy.getCityId(),
                        vacancy.getFileId())
        ) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
