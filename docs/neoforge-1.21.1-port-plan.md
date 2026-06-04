# Nycto NeoForge 1.21.1 Port Plan

Target: native NeoForge for Minecraft 1.21.1.

Required gameplay changes:
- Vampires must not take sunlight damage or flee sunlight.
- Vampire skill selection must not require or auto-apply weakness side effects.
- Chinese localization must be present and valid UTF-8 JSON.

Parallel workers:
- Worker 1: Gradle, NeoForge metadata, entrypoints, mixin metadata.
- Worker 2: core API and persistent player/entity/level state.
- Worker 3: registries, items, blocks, effects, tags, recipe serializers.
- Worker 4: transformations, vampire powers, entities, AI, gameplay events.
- Worker 5: client setup, screens, menus, renderers, payload networking.
- Worker 6: resources, generated data, localization, resource validation.

Integration order:
1. Apply Worker 1 first so the project has a NeoForge 1.21.1 build spine.
2. Apply Worker 3 next to establish registry names and common object references.
3. Apply Worker 2 before gameplay/client code so state access has one source of truth.
4. Apply Worker 4 and resolve server-side compile errors around powers/entities/events.
5. Apply Worker 5 and resolve networking, menu, and client-only compile errors.
6. Apply Worker 6 last for resource path fixes, localization, and datagen cleanup.

Verification gates:
- `zh_cn.json` parses as UTF-8 JSON.
- Gradle reaches Java compilation on Java 21.
- Final `build` produces a NeoForge jar for Minecraft 1.21.1.
- In-game smoke test checklist: vampire transform, altar skill selection without weakness, daytime vampire survival, basic entity spawn.
