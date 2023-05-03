List<SearchParam> searchParams = new ArrayList<>();
searchParams.add(new SearchParam("name", "John", Operation.EQUAL));
searchParams.add(new SearchParam("email", "john@example.com", Operation.EQUAL));
searchParams.add(new SearchParam("age", new Integer[]{25, 35}, Operation.BETWEEN));

int page = 0;
int size = 10;
String sort = "name";
String order = "ASC";

SearchResult<User> searchResult = userDao.search(searchParams, page, size, sort, order);

// Lấy kết quả tìm kiếm và thông tin phân trang
List<User> content = searchResult.getContent();
int totalPages = searchResult.getTotalPages();
long totalElements = searchResult.getTotalElements();