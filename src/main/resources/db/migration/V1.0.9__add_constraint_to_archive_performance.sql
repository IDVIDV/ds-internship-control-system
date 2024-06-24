ALTER TABLE archive.performance
ADD CONSTRAINT archive_performance_user_id_fk
    FOREIGN KEY (user_id)
        REFERENCES archive.user (user_id)
        ON DELETE CASCADE,
ADD CONSTRAINT archive_performance_internship_id_fk
    FOREIGN KEY (internship_id)
    REFERENCES archive.internship (internship_id)
    ON DELETE CASCADE;