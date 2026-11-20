-- ================================================================
-- Init Script for mj_auth_db
-- Auth Service Database - Authentification & User Management
-- ================================================================

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    User_Id        SERIAL       NOT NULL,
    User_FirstName VARCHAR(255) DEFAULT NULL,
    User_LastName  VARCHAR(255) DEFAULT NULL,
    User_Phone     VARCHAR(255) DEFAULT NULL,
    User_Email     VARCHAR(255) NOT NULL,
    User_Password  VARCHAR(255) NOT NULL,
    PRIMARY KEY (User_Id),
    UNIQUE (User_Email)
);

-- Create roles table (for future role-based access control)
CREATE TABLE IF NOT EXISTS roles (
    Role_Id   SERIAL       NOT NULL,
    Role_Name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (Role_Id)
);

-- Create user_roles mapping table
CREATE TABLE IF NOT EXISTS user_roles (
    User_Id INTEGER NOT NULL,
    Role_Id INTEGER NOT NULL,
    PRIMARY KEY (User_Id, Role_Id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (User_Id)
        REFERENCES users (User_Id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (Role_Id)
        REFERENCES roles (Role_Id) ON DELETE CASCADE
);

-- Insert default roles
INSERT INTO roles (Role_Name) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO roles (Role_Name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(User_Email);
CREATE INDEX IF NOT EXISTS idx_user_roles_user ON user_roles(User_Id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role ON user_roles(Role_Id);

-- ================================================================
-- Initial Data (Optional - Add test user if needed)
-- ================================================================
-- Uncomment to add initial test user
-- INSERT INTO users (User_FirstName, User_LastName, User_Email, User_Password)
-- VALUES ('Test', 'User', 'test@example.com', '$2a$10$...');
