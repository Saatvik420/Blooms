import React, { useEffect, useState } from "react";
import { api } from "./apiClient";

const initialRegister = { name: "", email: "", password: "", phone: "" };
const initialLogin = { phone: "", password: "" };
const initialCategory = { title: "", desc: "", categoryUrl: "" };
const initialSubCategory = { categoryId: "", name: "", description: "" };
const initialCategoryModel = {
  id: "",
  name: "",
  description: "",
  imageUrl: "",
  active: true,
  status: "",
  createdBy: ""
};
const initialSubCategoryModel = {
  id: "",
  categoryId: "",
  name: "",
  description: "",
  active: true,
  status: "",
  createdBy: ""
};
const initialBlogModel = {
  id: "",
  title: "",
  description: "",
  content: "",
  status: "",
  authorId: ""
};

function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [registerForm, setRegisterForm] = useState(initialRegister);
  const [loginForm, setLoginForm] = useState(initialLogin);
  const [categoryForm, setCategoryForm] = useState(initialCategory);
  const [subCategoryForm, setSubCategoryForm] = useState(initialSubCategory);
  const [categories, setCategories] = useState([]);
  const [subCategories, setSubCategories] = useState([]);
  const [blogCategories, setBlogCategories] = useState([]);
  const [activeTab, setActiveTab] = useState("login");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [viewMode, setViewMode] = useState("simple"); // "simple" | "manage" | "saas"
  const [manageTab, setManageTab] = useState("categories"); // "categories" | "subcategories" | "blogs"
  const [modalType, setModalType] = useState(null); // e.g. "category-create", "category-edit", ...
  const [modalData, setModalData] = useState(null);
  const [modalSubmitting, setModalSubmitting] = useState(false);

  useEffect(() => {
    if (currentUser) {
      fetchCategories();
      fetchSubCategories();
      fetchBlogCategories();
    }
  }, [currentUser]);

  const handleRegisterChange = (e) => {
    setRegisterForm({ ...registerForm, [e.target.name]: e.target.value });
  };

  const handleLoginChange = (e) => {
    setLoginForm({ ...loginForm, [e.target.name]: e.target.value });
  };

  const handleCategoryChange = (e) => {
    setCategoryForm({ ...categoryForm, [e.target.name]: e.target.value });
  };

  const handleSubCategoryChange = (e) => {
    setSubCategoryForm({ ...subCategoryForm, [e.target.name]: e.target.value });
  };

  const openCategoryCreateModal = () => {
    setModalType("category-create");
    setModalData({
      ...initialCategoryModel,
      createdBy: currentUser?.name || ""
    });
  };

  const openCategoryEditModal = (categoryRow) => {
    // categoryRow shape is CategoryResponse: id, title, desc, categoryUrl
    setModalType("category-edit");
    setModalData({
      ...initialCategoryModel,
      id: categoryRow.id || "",
      name: categoryRow.title || "",
      description: categoryRow.desc || "",
      imageUrl: categoryRow.categoryUrl || "",
      createdBy: currentUser?.name || ""
    });
  };

  const openCategoryDeleteModal = (categoryRow) => {
    setModalType("category-delete");
    setModalData({
      id: categoryRow.id || ""
    });
  };

  const openSubCategoryCreateModal = () => {
    setModalType("subcategory-create");
    setModalData({
      ...initialSubCategoryModel
    });
  };

  const openSubCategoryEditModal = () => {
    // For now, let the user type the ID they want to edit
    setModalType("subcategory-edit");
    setModalData({
      ...initialSubCategoryModel
    });
  };

  const openSubCategoryDeleteModal = () => {
    setModalType("subcategory-delete");
    setModalData({
      id: ""
    });
  };

  const openBlogCreateModal = () => {
    setModalType("blog-create");
    setModalData({
      ...initialBlogModel,
      authorId: currentUser?.id || ""
    });
  };

  const openBlogEditModal = () => {
    setModalType("blog-edit");
    setModalData({
      ...initialBlogModel
    });
  };

  const openBlogDeleteModal = () => {
    setModalType("blog-delete");
    setModalData({
      id: ""
    });
  };

  const closeModal = () => {
    setModalType(null);
    setModalData(null);
    setModalSubmitting(false);
  };

  const handleModalChange = (e) => {
    const { name, value, type, checked } = e.target;
    if (!modalData) return;
    setModalData({
      ...modalData,
      [name]: type === "checkbox" ? checked : value
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      await api.post("/account/signup", registerForm);
      setMessage("Account created! You can now login with your name.");
      setRegisterForm(initialRegister);
      setActiveTab("login");
    } catch (err) {
      console.error(err);
      setError("Failed to register account.");
    }
  };

  // Login using phone + password – backend can implement /api/account/login
  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      const res = await api.post("/account/login", loginForm);
      if (!res.data) {
        setError("No user found with that mobile/password.");
        return;
      }
      setCurrentUser(res.data);
      setMessage(`Logged in as ${res.data.name || res.data.email || "user"}`);
    } catch (err) {
      console.error(err);
      setError(
        "Login failed. (If /api/account/login is not implemented yet, this is expected.)"
      );
    }
  };

  const fetchCategories = async () => {
    try {
      const res = await api.get("/categories/all");
      setCategories(res.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to load categories.");
    }
  };

  const fetchSubCategories = async () => {
    try {
      const res = await api.get("/subcategories/all");
      setSubCategories(res.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to load subcategories.");
    }
  };

  const fetchBlogCategories = async () => {
    try {
      const res = await api.get("/blogs/categories");
      setBlogCategories(res.data || []);
    } catch (err) {
      // This endpoint is just for demo; ignore errors silently
      console.warn("Could not load blog category details", err);
    }
  };

  const handleCreateCategory = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      await api.post("/categories", categoryForm);
      setMessage("Category created.");
      setCategoryForm(initialCategory);
      fetchCategories();
    } catch (err) {
      console.error(err);
      setError("Failed to create category.");
    }
  };

  const handleDeleteCategory = async (id) => {
    setMessage("");
    setError("");
    try {
      await api.delete("/categories", { params: { categoryId: id } });
      setMessage("Category deleted (soft delete).");
      fetchCategories();
    } catch (err) {
      console.error(err);
      setError("Failed to delete category.");
    }
  };

  const handleCreateSubCategory = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      // Switched to JSON for consistency
      await api.post("/subcategories", subCategoryForm);
      setMessage("SubCategory create request sent (see backend logs/db).");
      setSubCategoryForm(initialSubCategory);
      fetchBlogCategories();
      fetchSubCategories();
    } catch (err) {
      console.error(err);
      setError("Failed to create subcategory.");
    }
  };

  const handleLogout = () => {
    setCurrentUser(null);
    setCategories([]);
    setSubCategories([]);
    setBlogCategories([]);
    setMessage("");
    setError("");
  };

  const handleModalSubmit = async (e) => {
    e.preventDefault();
    if (!modalType || !modalData) return;
    setModalSubmitting(true);
    setMessage("");
    setError("");
    try {
      switch (modalType) {
        case "category-create":
          await api.post("/categories", modalData);
          await fetchCategories();
          setMessage("Category create API called.");
          break;
        case "category-edit":
          await api.put(`/categories/${modalData.id}`, modalData);
          await fetchCategories();
          setMessage("Category update API called.");
          break;
        case "category-delete":
          await api.delete(`/categories/${modalData.id}`);
          await fetchCategories();
          setMessage("Category delete API called.");
          break;
        case "subcategory-create":
          await api.post("/subcategories", modalData);
          setMessage("SubCategory create API called.");
          await fetchSubCategories();
          break;
        case "subcategory-edit":
          await api.put("/subcategories", modalData);
          setMessage("SubCategory update API called.");
          await fetchSubCategories();
          break;
        case "subcategory-delete":
          await api.delete(`/subcategories/${modalData.id}`);
          setMessage("SubCategory delete API called.");
          await fetchSubCategories();
          break;
        case "blog-create":
          await api.post("/blogs", modalData);
          setMessage("Blog create API called.");
          break;
        case "blog-edit":
          await api.put(`/blogs/${modalData.id}`, modalData);
          setMessage("Blog update API called.");
          break;
        case "blog-delete":
          await api.delete(`/blogs/${modalData.id}`);
          setMessage("Blog delete API called.");
          break;
        default:
          break;
      }
      closeModal();
    } catch (err) {
      console.error(err);
      setError(
        "API call failed (this is expected if the endpoint is not implemented yet)."
      );
      setModalSubmitting(false);
    }
  };

  return (
    <div className="app">
      <header className="app-header">
        <h1>Blooms Demo UI</h1>
        <p className="app-subtitle">
          Simple React frontend talking to the existing Spring Boot APIs.
        </p>
      </header>

      {message && <div className="alert success">{message}</div>}
      {error && <div className="alert error">{error}</div>}

      {!currentUser ? (
        <Landing
          activeTab={activeTab}
          setActiveTab={setActiveTab}
          registerForm={registerForm}
          onRegisterChange={handleRegisterChange}
          onRegister={handleRegister}
          loginForm={loginForm}
          onLoginChange={handleLoginChange}
          onLogin={handleLogin}
        />
      ) : (
        <>
          <div className="view-toggle">
            <button
              className={viewMode === "simple" ? "tab active" : "tab"}
              onClick={() => setViewMode("simple")}
            >
              Quick Demo
            </button>
            <button
              className={viewMode === "manage" ? "tab active" : "tab"}
              onClick={() => setViewMode("manage")}
            >
              Manage (Tabs)
            </button>
            <button
              className={viewMode === "saas" ? "tab active" : "tab"}
              onClick={() => setViewMode("saas")}
            >
              App Dashboard
            </button>
          </div>

          {viewMode === "simple" ? (
            <Dashboard
              user={currentUser}
              categories={categories}
              blogCategories={blogCategories}
              categoryForm={categoryForm}
              onCategoryChange={handleCategoryChange}
              onCreateCategory={handleCreateCategory}
              onDeleteCategory={handleDeleteCategory}
              subCategoryForm={subCategoryForm}
              onSubCategoryChange={handleSubCategoryChange}
              onCreateSubCategory={handleCreateSubCategory}
              onLogout={handleLogout}
            />
          ) : viewMode === "manage" ? (
            <ManageTabs
              user={currentUser}
              manageTab={manageTab}
              setManageTab={setManageTab}
              categories={categories}
              blogCategories={blogCategories}
              categoryForm={categoryForm}
              onCategoryChange={handleCategoryChange}
              onCreateCategory={handleCreateCategory}
              onDeleteCategory={handleDeleteCategory}
              subCategoryForm={subCategoryForm}
              onSubCategoryChange={handleSubCategoryChange}
              onCreateSubCategory={handleCreateSubCategory}
              onLogout={handleLogout}
            />
          ) : (
            <SaaSDashboard
              user={currentUser}
              categories={categories}
              subCategories={subCategories}
              blogCategories={blogCategories}
              onOpenCategoryCreate={openCategoryCreateModal}
              onOpenCategoryEdit={openCategoryEditModal}
              onOpenCategoryDelete={openCategoryDeleteModal}
              onOpenSubCategoryCreate={openSubCategoryCreateModal}
              onOpenSubCategoryEdit={openSubCategoryEditModal}
              onOpenSubCategoryDelete={openSubCategoryDeleteModal}
              onOpenBlogCreate={openBlogCreateModal}
              onOpenBlogEdit={openBlogEditModal}
              onOpenBlogDelete={openBlogDeleteModal}
              onLogout={handleLogout}
            />
          )}
        </>
      )}
      {modalType && modalData && (
        <div className="modal-backdrop">
          <div className="modal">
            <h3>
              {modalType === "category-create" && "Create Category (Model Fields)"}
              {modalType === "category-edit" && "Edit Category (Model Fields)"}
              {modalType === "category-delete" && "Delete Category"}
              {modalType === "subcategory-create" &&
                "Create SubCategory (Model Fields)"}
              {modalType === "subcategory-edit" &&
                "Edit SubCategory (Model Fields)"}
              {modalType === "subcategory-delete" && "Delete SubCategory"}
              {modalType === "blog-create" && "Create Blog (Model Fields)"}
              {modalType === "blog-edit" && "Edit Blog (Model Fields)"}
              {modalType === "blog-delete" && "Delete Blog"}
            </h3>
            <p className="form-help">
              This form maps 1:1 to the Java model. On submit the UI calls the
              corresponding REST endpoint. If that endpoint doesn&apos;t exist yet, the
              backend will return 404, which you can use as a teaching moment.
            </p>
            <form onSubmit={handleModalSubmit} className="form">
              {(modalType === "category-create" ||
                modalType === "category-edit" ||
                modalType === "category-delete" ||
                modalType === "subcategory-create" ||
                modalType === "subcategory-edit" ||
                modalType === "subcategory-delete" ||
                modalType === "blog-edit" ||
                modalType === "blog-delete") && (
                <label>
                  ID
                  <input
                    name="id"
                    value={modalData.id || ""}
                    onChange={handleModalChange}
                    required={
                      modalType === "category-edit" ||
                      modalType === "category-delete" ||
                      modalType === "subcategory-edit" ||
                      modalType === "subcategory-delete" ||
                      modalType === "blog-edit" ||
                      modalType === "blog-delete"
                    }
                  />
                </label>
              )}

              {(modalType === "category-create" ||
                modalType === "category-edit") && (
                <>
                  <label>
                    Name
                    <input
                      name="name"
                      value={modalData.name || ""}
                      onChange={handleModalChange}
                      required
                    />
                  </label>
                  <label>
                    Description
                    <textarea
                      name="description"
                      value={modalData.description || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Image URL
                    <input
                      name="imageUrl"
                      value={modalData.imageUrl || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label className="checkbox-row">
                    <input
                      type="checkbox"
                      name="active"
                      checked={!!modalData.active}
                      onChange={handleModalChange}
                    />
                    Active
                  </label>
                  <label>
                    Status
                    <input
                      name="status"
                      value={modalData.status || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Created By
                    <input
                      name="createdBy"
                      value={modalData.createdBy || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                </>
              )}

              {(modalType === "subcategory-create" ||
                modalType === "subcategory-edit") && (
                <>
                  <label>
                    Category ID
                    <input
                      name="categoryId"
                      value={modalData.categoryId || ""}
                      onChange={handleModalChange}
                      required
                    />
                  </label>
                  <label>
                    Name
                    <input
                      name="name"
                      value={modalData.name || ""}
                      onChange={handleModalChange}
                      required
                    />
                  </label>
                  <label>
                    Description
                    <textarea
                      name="description"
                      value={modalData.description || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label className="checkbox-row">
                    <input
                      type="checkbox"
                      name="active"
                      checked={!!modalData.active}
                      onChange={handleModalChange}
                    />
                    Active
                  </label>
                  <label>
                    Status
                    <input
                      name="status"
                      value={modalData.status || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Created By
                    <input
                      name="createdBy"
                      value={modalData.createdBy || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                </>
              )}

              {(modalType === "blog-create" || modalType === "blog-edit") && (
                <>
                  <label>
                    Title
                    <input
                      name="title"
                      value={modalData.title || ""}
                      onChange={handleModalChange}
                      required
                    />
                  </label>
                  <label>
                    Short Description
                    <input
                      name="description"
                      value={modalData.description || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Content
                    <textarea
                      name="content"
                      value={modalData.content || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Status
                    <input
                      name="status"
                      value={modalData.status || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                  <label>
                    Author ID
                    <input
                      name="authorId"
                      value={modalData.authorId || ""}
                      onChange={handleModalChange}
                    />
                  </label>
                </>
              )}

              {(modalType === "category-delete" ||
                modalType === "subcategory-delete" ||
                modalType === "blog-delete") && (
                <p className="form-help">
                  This will call the corresponding <code>DELETE</code> endpoint
                  for the given ID.
                </p>
              )}

              <div className="modal-actions">
                <button
                  type="button"
                  className="btn"
                  onClick={closeModal}
                  disabled={modalSubmitting}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn primary"
                  disabled={modalSubmitting}
                >
                  {modalSubmitting ? "Submitting..." : "Submit"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

function Landing({
  activeTab,
  setActiveTab,
  registerForm,
  onRegisterChange,
  onRegister,
  loginForm,
  onLoginChange,
  onLogin
}) {
  return (
    <div className="card">
      <div className="tabs">
        <button
          className={activeTab === "login" ? "tab active" : "tab"}
          onClick={() => setActiveTab("login")}
        >
          Login
        </button>
        <button
          className={activeTab === "register" ? "tab active" : "tab"}
          onClick={() => setActiveTab("register")}
        >
          Register
        </button>
      </div>

      {activeTab === "login" ? (
        <form onSubmit={onLogin} className="form">
          <p className="form-help">
            Login now uses <strong>mobile number</strong> and{" "}
            <strong>password</strong>. The UI calls{" "}
            <code>POST /api/account/login</code> with these two fields.
          </p>
          <label>
            Mobile
            <input
              name="phone"
              value={loginForm.phone}
              onChange={onLoginChange}
              required
            />
          </label>
          <label>
            Password
            <input
              type="password"
              name="password"
              value={loginForm.password}
              onChange={onLoginChange}
              required
            />
          </label>
          <button type="submit" className="btn primary">
            Login
          </button>
        </form>
      ) : (
        <form onSubmit={onRegister} className="form">
          <p className="form-help">
            This sends a <code>POST /api/account/signup</code> to the backend.
          </p>
          <label>
            Name
            <input
              name="name"
              value={registerForm.name}
              onChange={onRegisterChange}
              required
            />
          </label>
          <label>
            Email
            <input
              type="email"
              name="email"
              value={registerForm.email}
              onChange={onRegisterChange}
              required
            />
          </label>
          <label>
            Password
            <input
              type="password"
              name="password"
              value={registerForm.password}
              onChange={onRegisterChange}
              required
            />
          </label>
          <label>
            Phone
            <input
              name="phone"
              value={registerForm.phone}
              onChange={onRegisterChange}
            />
          </label>
          <button type="submit" className="btn primary">
            Register
          </button>
        </form>
      )}
    </div>
  );
}

function Dashboard({
  user,
  categories,
  blogCategories,
  categoryForm,
  onCategoryChange,
  onCreateCategory,
  onDeleteCategory,
  subCategoryForm,
  onSubCategoryChange,
  onCreateSubCategory,
  onLogout
}) {
  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <div>
          <h2>Welcome, {user.name}</h2>
          <p className="muted">
            You are now \"logged in\" on the frontend. All actions below call the
            Spring Boot APIs.
          </p>
        </div>
        <button className="btn" onClick={onLogout}>
          Logout
        </button>
      </div>

      <div className="grid">
        <div className="card">
          <h3>Create Category</h3>
          <p className="form-help">
            Calls <code>POST /api/categories</code> with a JSON body matching{" "}
            <code>CategoryRequest</code>.
          </p>
          <form onSubmit={onCreateCategory} className="form">
            <label>
              Title
              <input
                name="title"
                value={categoryForm.title}
                onChange={onCategoryChange}
                required
              />
            </label>
            <label>
              Description
              <textarea
                name="desc"
                value={categoryForm.desc}
                onChange={onCategoryChange}
              />
            </label>
            <label>
              Image URL
              <input
                name="categoryUrl"
                value={categoryForm.categoryUrl}
                onChange={onCategoryChange}
              />
            </label>
            <button type="submit" className="btn primary">
              Create Category
            </button>
          </form>
        </div>

        <div className="card">
          <h3>Create SubCategory</h3>
          <p className="form-help">
            Sends a form-url-encoded request to{" "}
            <code>POST /api/subcategories</code> for the{" "}
            <code>SubCategoryRequest</code> DTO.
          </p>
          <form onSubmit={onCreateSubCategory} className="form">
            <label>
              Parent Category ID
              <input
                name="categoryId"
                value={subCategoryForm.categoryId}
                onChange={onSubCategoryChange}
                required
              />
            </label>
            <label>
              Name
              <input
                name="name"
                value={subCategoryForm.name}
                onChange={onSubCategoryChange}
                required
              />
            </label>
            <label>
              Description
              <textarea
                name="description"
                value={subCategoryForm.description}
                onChange={onSubCategoryChange}
              />
            </label>
            <button type="submit" className="btn primary">
              Create SubCategory
            </button>
          </form>
        </div>
      </div>

      <div className="grid">
        <div className="card">
          <h3>Current Categories</h3>
          <p className="form-help">
            Data from <code>GET /api/categories/all</code>. Delete uses{" "}
            <code>DELETE /api/categories?categoryId=...</code>.
          </p>
          {categories.length === 0 ? (
            <p className="muted">No categories yet.</p>
          ) : (
            <ul className="list">
              {categories.map((c) => (
                <li key={c.id} className="list-item">
                  <div>
                    <strong>{c.title}</strong>
                    <div className="muted small">{c.desc}</div>
                    <div className="muted small">ID: {c.id}</div>
                  </div>
                  <button
                    className="btn small"
                    onClick={() => onDeleteCategory(c.id)}
                  >
                    Delete
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="card">
          <h3>Blog Category Tree (Read-only)</h3>
          <p className="form-help">
            Demonstrates a read-only API: <code>GET /api/blogs/categories</code>.
            This shows categories with their subcategories as returned by the backend.
          </p>
          {blogCategories.length === 0 ? (
            <p className="muted">
              No blog category details returned yet or endpoint not fully
              implemented.
            </p>
          ) : (
            <ul className="list">
              {blogCategories.map((cat) => (
                <li key={cat.categoryId}>
                  <strong>{cat.name}</strong>
                  {Array.isArray(cat.subCategoryDetailList) &&
                    cat.subCategoryDetailList.length > 0 && (
                      <ul className="nested-list">
                        {cat.subCategoryDetailList.map((sub) => (
                          <li key={sub.id || sub.name}>{sub.name}</li>
                        ))}
                      </ul>
                    )}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}

function ManageTabs({
  user,
  manageTab,
  setManageTab,
  categories,
  blogCategories,
  categoryForm,
  onCategoryChange,
  onCreateCategory,
  onDeleteCategory,
  subCategoryForm,
  onSubCategoryChange,
  onCreateSubCategory,
  onLogout
}) {
  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <div>
          <h2>Admin Management</h2>
          <p className="muted small">
            This screen is organized by tabs: Categories, Subcategories and Blogs,
            to clearly show how a UI can manage different resources via APIs.
          </p>
          <p className="muted small">
            Note: the current backend only exposes full CRUD for categories
            (create, list, soft-delete). Subcategories and blogs are limited by
            existing endpoints, so their edit/delete actions are intentionally
            left as future work.
          </p>
        </div>
        <button className="btn" onClick={onLogout}>
          Logout
        </button>
      </div>

      <div className="tabs manage-tabs">
        <button
          className={manageTab === "categories" ? "tab active" : "tab"}
          onClick={() => setManageTab("categories")}
        >
          Categories
        </button>
        <button
          className={manageTab === "subcategories" ? "tab active" : "tab"}
          onClick={() => setManageTab("subcategories")}
        >
          Subcategories
        </button>
        <button
          className={manageTab === "blogs" ? "tab active" : "tab"}
          onClick={() => setManageTab("blogs")}
        >
          Blogs
        </button>
      </div>

      {manageTab === "categories" && (
        <div className="grid">
          <div className="card">
            <h3>Categories – Create</h3>
            <p className="form-help">
              Create a category via <code>POST /api/categories</code>. Then use
              the list on the right to read and delete.
            </p>
            <form onSubmit={onCreateCategory} className="form">
              <label>
                Title
                <input
                  name="title"
                  value={categoryForm.title}
                  onChange={onCategoryChange}
                  required
                />
              </label>
              <label>
                Description
                <textarea
                  name="desc"
                  value={categoryForm.desc}
                  onChange={onCategoryChange}
                />
              </label>
              <label>
                Image URL
                <input
                  name="categoryUrl"
                  value={categoryForm.categoryUrl}
                  onChange={onCategoryChange}
                />
              </label>
              <button type="submit" className="btn primary">
                Add Category
              </button>
            </form>
          </div>

          <div className="card">
            <h3>Categories – Read / Delete</h3>
            <p className="form-help">
              Reads from <code>GET /api/categories/all</code> and deletes via{" "}
              <code>DELETE /api/categories?categoryId=...</code>.
            </p>
            {categories.length === 0 ? (
              <p className="muted">No categories yet.</p>
            ) : (
              <ul className="list">
                {categories.map((c) => (
                  <li key={c.id} className="list-item">
                    <div>
                      <strong>{c.title}</strong>
                      <div className="muted small">{c.desc}</div>
                      <div className="muted small">ID: {c.id}</div>
                    </div>
                    <div className="list-actions">
                      <button
                        className="btn small"
                        onClick={() => onDeleteCategory(c.id)}
                      >
                        Delete
                      </button>
                      <button className="btn small" disabled>
                        Edit (needs backend PUT)
                      </button>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
      )}

      {manageTab === "subcategories" && (
        <div className="grid">
          <div className="card">
            <h3>Subcategories – Create</h3>
            <p className="form-help">
              Sends form data to <code>POST /api/subcategories</code> to create
              a new subcategory for a given category ID.
            </p>
            <form onSubmit={onCreateSubCategory} className="form">
              <label>
                Parent Category ID
                <input
                  name="categoryId"
                  value={subCategoryForm.categoryId}
                  onChange={onSubCategoryChange}
                  required
                />
              </label>
              <label>
                Name
                <input
                  name="name"
                  value={subCategoryForm.name}
                  onChange={onSubCategoryChange}
                  required
                />
              </label>
              <label>
                Description
                <textarea
                  name="description"
                  value={subCategoryForm.description}
                  onChange={onSubCategoryChange}
                />
              </label>
              <button type="submit" className="btn primary">
                Add Subcategory
              </button>
            </form>
          </div>

          <div className="card">
            <h3>Subcategories – Read / Edit / Delete</h3>
            <p className="form-help">
              Reads from <code>GET /api/subcategories/all</code>.
            </p>
            {subCategories.length === 0 ? (
              <p className="muted">No subcategories yet.</p>
            ) : (
              <ul className="list">
                {subCategories.map((sc) => (
                  <li key={sc.id} className="list-item">
                    <div>
                      <strong>{sc.name}</strong>
                      <div className="muted small">{sc.description}</div>
                      <div className="muted small">CatID: {sc.categoryId}</div>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
      )}

      {manageTab === "blogs" && (
        <div className="grid">
          <div className="card">
            <h3>Blogs – Read</h3>
            <p className="form-help">
              Uses <code>GET /api/blogs/categories</code> to show how blogs
              could be organized by category and subcategory.
            </p>
            {blogCategories.length === 0 ? (
              <p className="muted">
                No blog category data returned yet. Once the backend populates
                this endpoint, the tree will appear here.
              </p>
            ) : (
              <ul className="list">
                {blogCategories.map((cat) => (
                  <li key={cat.categoryId}>
                    <strong>{cat.name}</strong>
                    {Array.isArray(cat.subCategoryDetailList) &&
                      cat.subCategoryDetailList.length > 0 && (
                        <ul className="nested-list">
                          {cat.subCategoryDetailList.map((sub) => (
                            <li key={sub.id || sub.name}>{sub.name}</li>
                          ))}
                        </ul>
                      )}
                  </li>
                ))}
              </ul>
            )}
          </div>

          <div className="card">
            <h3>Blogs – Add / Edit / Delete (Conceptual)</h3>
            <p className="form-help">
              The current backend does not yet expose blog CRUD endpoints,
              so this panel is intentionally conceptual. It&apos;s a good place to
              discuss with students what endpoints like{" "}
              <code>POST /api/blogs</code>, <code>PUT /api/blogs/&#123;id&#125;</code>{" "}
              and <code>DELETE /api/blogs/&#123;id&#125;</code> might look like,
              and how this UI would call them.
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

function SaaSDashboard({
  user,
  categories,
  subCategories,
  blogCategories,
  onOpenCategoryCreate,
  onOpenCategoryEdit,
  onOpenCategoryDelete,
  onOpenSubCategoryCreate,
  onOpenSubCategoryEdit,
  onOpenSubCategoryDelete,
  onOpenBlogCreate,
  onOpenBlogEdit,
  onOpenBlogDelete,
  onLogout
}) {
  const [section, setSection] = useState("categories"); // "categories" | "subcategories" | "blogs"

  const renderHeaderCta = () => {
    if (section === "categories") {
      return (
        <button className="btn primary" onClick={onOpenCategoryCreate}>
          + New Category
        </button>
      );
    }
    if (section === "subcategories") {
      return (
        <button className="btn primary" onClick={onOpenSubCategoryCreate}>
          + New Subcategory
        </button>
      );
    }
    return (
      <button className="btn primary" onClick={onOpenBlogCreate}>
        + New Blog
      </button>
    );
  };

  const renderBody = () => {
    if (section === "categories") {
      return (
        <div>
          <p className="form-help">
            SaaS-style view of categories. In a real app these CTAs would open
            dialogs or side panels that call <code>POST /api/categories</code>,{" "}
            <code>PUT /api/categories/&#123;id&#125;</code> and{" "}
            <code>DELETE /api/categories/&#123;id&#125;</code>.
          </p>
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {categories.length === 0 ? (
                <tr>
                  <td colSpan={4} className="muted small">
                    No categories yet. Use the other dashboard views to create a
                    few, or wire this CTA to the create API.
                  </td>
                </tr>
              ) : (
                categories.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.title}</td>
                    <td>{c.desc}</td>
                    <td>
                      <div className="list-actions">
                        <button
                          className="btn small"
                          onClick={() => onOpenCategoryEdit(c)}
                        >
                          Edit
                        </button>
                        <button
                          className="btn small"
                          onClick={() => onOpenCategoryDelete(c)}
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      );
    }

    if (section === "subcategories") {
      return (
        <div>
          <p className="form-help">
            This table is intentionally a mock-up. It&apos;s a good exercise for
            students to design and implement <code>/api/subcategories</code>{" "}
            endpoints and then plug them into this view for real data.
          </p>
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Category ID</th>
                <th>Name</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {subCategories.length === 0 ? (
                <tr>
                  <td colSpan={4} className="muted small">
                    No subcategories yet.
                  </td>
                </tr>
              ) : (
                subCategories.map((sc) => (
                  <tr key={sc.id}>
                    <td>{sc.id}</td>
                    <td>{sc.categoryId}</td>
                    <td>{sc.name}</td>
                    <td>
                      <div className="list-actions">
                        <button
                          className="btn small"
                          onClick={() => onOpenSubCategoryEdit(sc)}
                        >
                          Edit
                        </button>
                        <button
                          className="btn small"
                          onClick={() => onOpenSubCategoryDelete(sc)}
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      );
    }

    // blogs
    return (
      <div>
        <p className="form-help">
          This shows how blogs could be grouped by categories and subcategories
          using <code>GET /api/blogs/categories</code>. You can extend this to
          true blog CRUD with new endpoints like <code>POST /api/blogs</code>.
        </p>
        <table className="data-table">
          <thead>
            <tr>
              <th>Category</th>
              <th>Subcategories</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {blogCategories.length === 0 ? (
              <tr>
                <td colSpan={3} className="muted small">
                  No blog category data yet. Once the backend populates{" "}
                  <code>/api/blogs/categories</code>, rows will appear here.
                </td>
              </tr>
            ) : (
              blogCategories.map((cat) => (
                <tr key={cat.categoryId}>
                  <td>{cat.name}</td>
                  <td>
                    {Array.isArray(cat.subCategoryDetailList) &&
                    cat.subCategoryDetailList.length > 0 ? (
                      cat.subCategoryDetailList.map((sub, idx) => (
                        <span key={sub.id || sub.name}>
                          {sub.name}
                          {idx <
                            cat.subCategoryDetailList.length - 1 && ", "}
                        </span>
                      ))
                    ) : (
                      <span className="muted small">No subcategories</span>
                    )}
                  </td>
                  <td>
                    <div className="list-actions">
                      <button
                        className="btn small"
                        onClick={onOpenBlogEdit}
                      >
                        View Blogs
                      </button>
                      <button
                        className="btn small"
                        onClick={onOpenBlogDelete}
                      >
                        Configure
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    );
  };

  return (
    <div className="saas-layout">
      <aside className="saas-sidebar">
        <div className="saas-logo">Blooms</div>
        <div className="saas-user small">Signed in as {user.name}</div>
        <nav className="saas-nav">
          <button
            className={
              section === "categories" ? "saas-nav-item active" : "saas-nav-item"
            }
            onClick={() => setSection("categories")}
          >
            Categories
          </button>
          <button
            className={
              section === "subcategories"
                ? "saas-nav-item active"
                : "saas-nav-item"
            }
            onClick={() => setSection("subcategories")}
          >
            Subcategories
          </button>
          <button
            className={
              section === "blogs" ? "saas-nav-item active" : "saas-nav-item"
            }
            onClick={() => setSection("blogs")}
          >
            Blogs
          </button>
        </nav>
        <button className="btn small saas-logout" onClick={onLogout}>
          Logout
        </button>
      </aside>

      <main className="saas-main">
        <div className="saas-main-header">
          <div>
            <h2>
              {section === "categories"
                ? "Categories"
                : section === "subcategories"
                ? "Subcategories"
                : "Blogs"}
            </h2>
            <p className="muted small">
              SaaS-style dashboard view. Left navigation chooses the resource,
              and this table shows its records with CTAs for CRUD actions.
            </p>
          </div>
          {renderHeaderCta()}
        </div>
        <div className="saas-main-body">{renderBody()}</div>
      </main>
    </div>
  );
}

export default App;

