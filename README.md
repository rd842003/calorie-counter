# Calorie Counter — Assignment 6 Demo (Spring Boot)

Run:
```
mvn spring-boot:run
# http://localhost:8080
```

What works
- Dashboard: totals + progress bar + list of today's items
- Add Food (Quick Add): persists to H2 in-memory and redirects back to dashboard
- History: last 14 days seeded; table rendered (chart placeholder)

Notes
- Thymeleaf templates avoid method calls; controller precomputes percent
- Spring Boot 3.3.3, plugin version pinned
