package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dto.FileDto;
import ru.job4j.model.Vacancy;
import ru.job4j.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleVacancyService implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final FileService fileService;

    public SimpleVacancyService(VacancyRepository sql2oVacancyRepository, FileService fileService) {
        this.vacancyRepository = sql2oVacancyRepository;
        this.fileService = fileService;
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public boolean deleteById(int id) {
        var fileOptional = findById(id);
        boolean isVacancyDeleted = false;
        if (fileOptional.isPresent()) {
            isVacancyDeleted = vacancyRepository.deleteById(id);
            fileService.deleteById(fileOptional.get().getFileId());
        }
        return isVacancyDeleted;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdated = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }
}
