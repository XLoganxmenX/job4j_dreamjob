package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Vacancy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "70 000 rub."));
        save(new Vacancy(0, "Junior Java Developer", "90 000 rub."));
        save(new Vacancy(0, "Junior+ Java Developer", "110 000 rub."));
        save(new Vacancy(0, "Middle Java Developer", "150 000 rub."));
        save(new Vacancy(0, "Middle+ Java Developer", "200 000 rub."));
        save(new Vacancy(0, "Senior Java Developer", "250 000 rub."));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(
                        oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription())
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
