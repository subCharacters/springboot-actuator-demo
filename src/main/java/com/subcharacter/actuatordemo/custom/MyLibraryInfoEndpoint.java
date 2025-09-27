package com.subcharacter.actuatordemo.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

@Slf4j
// @Endpoint(id = "myLibraryInfo") Web과 jmx 둘 다 endpoint가 열린다.
// @JmxEndpoint(id = "myLibraryInfo") jmx만 열고 싶을때
@WebEndpoint(id = "myLibraryInfo") // web만 열고 싶을때
public class MyLibraryInfoEndpoint {


    /**
     * 파라미터 수신 방법 1
     * GetMapping과 비슷함. WriteOperation, DeleteOperation도 존재
     *
     * @param name
     * @param includeVersion
     * @return
     */
    @ReadOperation
    public List<LibraryInfo> getLibraryInfos(@Nullable String name, boolean includeVersion) {

        LibraryInfo libraryInfo = new LibraryInfo();
        libraryInfo.setName("logback");
        libraryInfo.setVersion("1.0.0");

        LibraryInfo libraryInfo2 = new LibraryInfo();
        libraryInfo2.setName("jackson");
        libraryInfo2.setVersion("2.0.0");

        List<LibraryInfo> list = Arrays.asList(libraryInfo, libraryInfo2);

        if (name != null) {
            list = list.stream()
                    .filter(
                    lib -> name.equals(lib.getName())
            ).toList();
        }

        if (!includeVersion) {
            list = list.stream()
                    .map(lib -> {
                        LibraryInfo temp = new LibraryInfo();
                        temp.setName(lib.getName());
                        return temp;
                    })
                    .toList();
        }

        return list;
    }

    /**
     * 파라미터 수신 방법 2
     * POSTMapping과 비슷함. 로그만 찍기 위해 void.
     * RequestBody를 사용하여 dto로 변환하는 방법은 사용불가하므로 풀어서 사용해야함.
     *
     * @param name
     * @param enableSomething
     */
    @WriteOperation
    public void changeSomething(String name, boolean enableSomething) {
        log.info("name:{}, enableSomething:{}", name, enableSomething);
    }


    /**
     * 파라미터 수신 방법3
     * http://localhost:8080/actuator/myLibraryInfo/{path} 일때 사용가능하다.
     * depth가 있는 경우 인수를 (@Selector(match = Selector.Match.ALL_REMAINING) String[] path1)
     * 로 설정하면 배열로 받아진다.
     * http://localhost:8080/actuator/myLibraryInfo/{path1}/{path2}/{path3}
     * 이라면 path1, path2, path3이 배열에 하나씩 들어간다.
     *
     * @param path1
     * @return
     */
    @ReadOperation
    public String getPathTest(@Selector String path1) {
        log.info("path1:{}", path1);
        return path1;
    }
}
