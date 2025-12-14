-- =========================
-- TABLA TEACHER
-- =========================
CREATE TABLE teacher (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         speciality VARCHAR(255) NOT NULL
);

-- =========================
-- TABLA MODULE
-- =========================
CREATE TABLE module (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        credits INTEGER NOT NULL,
                        teacher_id BIGINT,
                        CONSTRAINT fk_module_teacher
                            FOREIGN KEY (teacher_id)
                                REFERENCES teacher(id)
);

-- =========================
-- TABLA STUDENT
-- =========================
CREATE TABLE student (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE
);

-- =========================
-- TABLA STUDENT_MODULE
-- =========================
CREATE TABLE student_module (
                                student_id BIGINT NOT NULL,
                                module_id BIGINT NOT NULL,
                                PRIMARY KEY (student_id, module_id),
                                CONSTRAINT fk_sm_student
                                    FOREIGN KEY (student_id)
                                        REFERENCES student(id)
                                        ON DELETE CASCADE,
                                CONSTRAINT fk_sm_module
                                    FOREIGN KEY (module_id)
                                        REFERENCES module(id)
                                        ON DELETE CASCADE
);
