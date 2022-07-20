Stream.of(
Block.box(0, 3, 0, 16, 8, 12),
Block.box(0, 0, 0, 16, 8, 16),
Block.box(0, 8, 11, 16, 16, 16)
).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();