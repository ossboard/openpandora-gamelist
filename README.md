
# 실행방법

java 1.8 추천 설치후,
ex)
https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
예로, 쿠키 허용후, Windows x64 버전 설치


### gameList.xml  -> gameList.xls (xml 파일을 엑셀파일로 변환 ) 
```
> java -jar build/libs/openpandora-gamelist.jar gameList.xml gameList.xls
```

### 변환된 xls 파일을 엑셀을 이용해 편집
- 한글화 ( 번역기 연동 가능 )
- 이름변경 및 정렬
- 카테고리등 일괄 변경가능

### gameList.xls -> gameList.xml (엑셀파일을 xml파일로 변환 ) 
```
> java -jar build/libs/openpandora-gamelist.jar gameList.xls gameList.xml
** 이름 덮어쓰기 주위!!
```
