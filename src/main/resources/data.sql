-- 대주제 삽입
INSERT INTO interest_major (major_name) VALUES ('문화 · 예술') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('액티비티') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('푸드 · 드링크') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('취미') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('파티 · 소개팅') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('여행 · 동행') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('자기계발') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('동네 · 친목') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('재테크') ON DUPLICATE KEY UPDATE major_name = major_name;
INSERT INTO interest_major (major_name) VALUES ('외국어') ON DUPLICATE KEY UPDATE major_name = major_name;

-- 문화 · 예술
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('전시', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('영화', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('페스티벌', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('공연', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('콘서트', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('뮤지컬', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('연극', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('연주회', 1) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 액티비티
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('등산', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('러닝', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('산책', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('야구', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('배드민턴', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('클라이밍', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('헬스', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('요가', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('수영', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('볼링', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('서핑', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('풋살', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('다이어트', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('자전거', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('플로깅', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('필라테스', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('다이빙', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('골프', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('농구', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('축구', 2) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 푸드 · 드링크
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('맛집투어', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('카페', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('와인', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('맥주', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('요리', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('위스키', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('칵테일', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('커피', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('디저트', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('전통주', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('티룸', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('파인다이닝', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('비건', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('페어링', 3) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 취미
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('사진', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('보드게임', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('공예', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('드로잉', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('댄스', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('노래', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('글쓰기', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('봉사활동', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('악기연주', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('방탈출', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('음악감상', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('만화', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('게임', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('명상', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('반려동물', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('캘리그라피', 4) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 파티 · 소개팅
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('소개팅', 5) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('파티', 5) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 여행 · 동행
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('국내여행', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('해외여행', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('캠핑', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('드라이브', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('피크닉', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('테마파크', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('복합문화공간', 6) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 자기계발
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('독서', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('스터디', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('대화', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('사이드 프로젝트', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('커리어', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('브랜딩', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('창작', 7) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 동네 · 친목
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('동네', 8) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('관심사', 8) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('또래', 8) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 재테크
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('N잡', 9) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('재테크', 9) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('부동산', 9) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('창업', 9) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('주식', 9) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;

-- 외국어
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('영어', 10) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('일본어', 10) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('중국어', 10) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
INSERT INTO interest_minor (minor_name, interest_major_id) VALUES ('언어교환', 10) ON DUPLICATE KEY UPDATE minor_name = minor_name, interest_major_id = interest_major_id;
