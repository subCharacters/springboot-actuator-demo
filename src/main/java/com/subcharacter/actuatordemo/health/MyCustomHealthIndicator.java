package com.subcharacter.actuatordemo.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 헬스 정보를 얻기 위한 커스텀 인디케이터 작성.
 * Actuator를 지원하지 않는 프로그램일 경우 커스텀으로 작성하면 된다.
 */
@Component
public class MyCustomHealthIndicator implements HealthIndicator {
    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        // 해당 프로그램의 정보를 받았다고 가정 한 후 아래 코드를 작성.
        boolean status = getStatus();
        Health build = null;
        if (status) {
            build = Health.up()
                    .withDetail("status", "OK")
                    .withDetail("message", "OK")
                    .build();
        } else {
            // 이게 활성화 될 경우 다른 스테이터스가 업이라도 최상위 스테이터스는 다운이 된다.
            // 다운일 경우 503 에러로 반환.
            build = Health.down()
                    .withDetail("status", "OK")
                    .withDetail("message", "OK")
                    .build();
        }


        return build;
    }

    boolean getStatus() {
        if (System.currentTimeMillis() % 2 == 0) {
            return true;
        }
        return false;
    }
}
