DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_schema = 'public'
      AND table_name = 'motorcycles'
      AND column_name = 'photo_url'
  ) THEN
    EXECUTE 'ALTER TABLE public.motorcycles ALTER COLUMN photo_url TYPE TEXT';
  END IF;
END $$;

DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_schema = 'public'
      AND table_name = 'users'
      AND column_name = 'photo_url'
  ) THEN
    EXECUTE 'ALTER TABLE public.users ALTER COLUMN photo_url TYPE TEXT';
  END IF;
END $$;
