package com.example.grocery_store_sales_online.repository.file;

import com.example.grocery_store_sales_online.model.File.FileEntry;
import com.example.grocery_store_sales_online.model.File.QFileEntry;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class FileEntryRepository extends BaseRepository<FileEntry,Long> implements IFileEntryRepository {
    protected QFileEntry fileEntry = QFileEntry.fileEntry;
    public FileEntryRepository( EntityManager em) {
        super(FileEntry.class, em);
    }
}
